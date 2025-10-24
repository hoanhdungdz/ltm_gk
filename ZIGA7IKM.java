// [Mã câu hỏi (qCode): ZIGA7IKM].  Một chương trình máy chủ cho phép kết nối qua TCP tại cổng 2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5s), yêu cầu xây dựng chương trình (tạm gọi là client) thực hiện kết nối tới server tại cổng 2207, sử dụng luồng byte dữ liệu (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự: 
// a.	Gửi chuỗi là mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;1D25ED92"
// b.	Nhận lần lượt hai số nguyên a và b từ server
// c.	Thực hiện tính toán tổng, tích và gửi lần lượt từng giá trị theo đúng thứ tự trên lên server
// d.	Đóng kết nối và kết thúc

package com.mycompany.ziga7ikm;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ZIGA7IKM {
    public static void main(String[] args) throws Exception {
        String host = "203.162.10.109";
        int port = 2207;
        String studentCode = "B22DCVT090"; 
        String qCode = "ZIGA7IKM";

        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(host, port), 5000);
        socket.setSoTimeout(5000);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        String request = studentCode + ";" + qCode;
        System.out.println(request);
        dos.writeUTF(request);
        dos.flush();

        int a = dis.readInt();
        int b = dis.readInt();
        System.out.println(a + b);

        int sum = a + b;
        int product = a * b;
        System.out.println(sum + product);
        dos.writeInt(sum);
        dos.writeInt(product);
        dos.flush();

        socket.close();
    }
}
