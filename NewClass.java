//[Mã câu hỏi (qCode): xgCu6KgB].  Một chương trình server cho phép 
//giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một 
//chương trình client trao đổi thông tin với server theo kịch bản:
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo 
//định dạng ";studentCode;qCode". Ví dụ: ";B21DCCN795;ylrhZ6UM".
//b. Nhận thông điệp là một chuỗi từ server theo định dạng 
//"requestId;n;k;z1,z2,...,zn", trong đó:
//    requestId là chuỗi ngẫu nhiên duy nhất.
//    n là số phần tử của mảng.
//    k là kích thước cửa sổ trượt (k < n).
//    z1 đến zn là n phần tử là số nguyên của mảng.
//c. Thực hiện tìm giá trị lớn nhất trong mỗi cửa sổ trượt với 
//kích thước k trên mảng số nguyên nhận được, và gửi thông điệp 
//lên server theo định dạng "requestId;max1,max2,...,maxm", 
//trong đó max1 đến maxm là các giá trị lớn nhất tương ứng trong mỗi cửa sổ.
//Ví dụ: "requestId;5;3;1,5,2,3,4"
//Kết quả: "requestId;5,5,4"
//d. Đóng socket và kết thúc chương trình.
package com.mycompany.xgcu6kgb;

import java.net.*;
import java.util.*;
import java.io.*;

public class NewClass {
    public static void main(String[] args) throws IOException{
        String server = "203.162.10.109";
        int port = 2207;
        String msv = ";B22DCVT090;xgCu6KgB";
        
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(5000);
        InetAddress host = InetAddress.getByName(server);
        
        byte[] sendData = msv.getBytes();
        socket.send(new DatagramPacket(sendData, msv.length(), host, port));
        System.out.println(msv);
        
        byte[] buf = new byte[1000];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String respond = new String(packet.getData(), 0, packet.getLength()).trim();
        System.out.println(respond);
        
        String[] p = respond.split(";");
        String requestId = p[0];
        int n = Integer.parseInt(p[1]);
        int k = Integer.parseInt(p[2]);
        int[] arr = Arrays.stream(p[3].split(",")).mapToInt(Integer::parseInt).toArray();
        
        List<Integer> max = slidingmax(arr, k);
        String result = requestId + ";" + String.join(",", max.stream().map(String::valueOf).toList());
        socket.send(new DatagramPacket(result.getBytes(), result.length(), host, port));
        System.out.println(result);
        socket.close();
    }
    private static List<Integer> slidingmax(int[] arr, int k) {
        List<Integer> res = new ArrayList<>();
        Deque<Integer> dq = new ArrayDeque<>();
        for(int i = 0; i < arr.length; i++) {
            while(!dq.isEmpty() && dq.peekFirst() <= i - k) dq.pollFirst();
            while(!dq.isEmpty() && arr[dq.peekLast()] < arr[i]) dq.pollLast();
            dq.offerLast(i);
            if(i >= k - 1) res.add(arr[dq.peekFirst()]);
        }
        return res;
    }
}
