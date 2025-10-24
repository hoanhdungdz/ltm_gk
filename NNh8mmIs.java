//[Mã câu hỏi (qCode): nNh8mmIs].  [Loại bỏ ký tự đặc biệt và ký tự trùng giữ nguyên thứ tự xuất hiện]
//Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208 . Yêu cầu là xây dựng một 
//chương trình client trao đổi thông tin với server theo kịch bản dưới đây:
//a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng 
//";studentCode;qCode”. Ví dụ: ";B15DCCN001;B34D51E0"
//b.	Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;str1;str2".
//•	requestId là chuỗi ngẫu nhiên duy nhất
//•	str1,str2 lần lượt là chuỗi thứ nhất và chuỗi thứ hai
//c.	Loại bỏ các ký tự trong chuỗi thứ nhất mà xuất hiện trong chuỗi thứ hai, giữ nguyên 
//thứ tự xuất hiện. Gửi thông điệp là một chuỗi lên server theo định dạng "requestId;strOutput", 
//trong đó chuỗi strOutput là chuỗi đã được xử lý ở trên.
//d.	Đóng socket và kết thúc chương trình.

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.nnh8mmis;
import java.util.*;
import java.io.*;
import java.net.*;
public class NNh8mmIs {
    public static void main(String[] args) throws Exception{
        String server = "203.162.10.109";
        int port = 2208;
        String msv = ";B22DCVT090;nNh8mmIs";
        
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
        String str1 = p[1];
        String str2 = p[2];
        
        String output = process(str1, str2);
        System.out.println(output);
        
        String result = requestId + ";" + output;
        socket.send(new DatagramPacket(result.getBytes(), result.length(), host, port));
        System.out.println(result);
        socket.close();
        
    }
    private static String process(String str1, String str2) {
        Set<Character> remove = new LinkedHashSet<>();
        for(char c : str2.toCharArray()) {
            remove.add(c);
        }
        StringBuilder sb = new StringBuilder();
        for(char c : str1.toCharArray()) {
            if(!remove.contains(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}


