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
