package com.mycompany.asemnxrp;

import TCP.Laptop;
import java.io.*;
import java.net.*;

public class AseMNXRP {
    public static void main(String[] args) throws Exception {
        String host = "203.162.10.109";
        int port = 2209;
        String studentCode = "B22DCVT090"; // 🔸 Thay mã sinh viên thật
        String qCode = "aseMNXRP";

        // 1️⃣ Kết nối tới server
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(host, port), 5000);
        socket.setSoTimeout(5000);

        // 2️⃣ Tạo Object Streams
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

        // (1) Gửi chuỗi "studentCode;qCode" tới server
        String request = studentCode + ";" + qCode;
        System.out.println("Sending: " + request);
        oos.writeObject(request);
        oos.flush();

        // (2) Nhận đối tượng Laptop từ server
        Object received = ois.readObject();
        if (!(received instanceof Laptop)) {
            System.out.println("Received unexpected object type.");
            socket.close();
            return;
        }

        Laptop laptop = (Laptop) received;
        System.out.println("Received Laptop: " + laptop);

        // (3) Sửa thông tin sai
        fixLaptop(laptop);
        System.out.println("Fixed Laptop: " + laptop);

        // Gửi đối tượng đã sửa về server
        oos.writeObject(laptop);
        oos.flush();

        // (4) Đóng kết nối
        oos.close();
        ois.close();
        socket.close();
        System.out.println("Connection closed safely.");
    }

    // Hàm xử lý lỗi đảo tên và đảo số lượng
    private static void fixLaptop(Laptop laptop) {
        // a) Đảo lại thứ tự từ trong name
        String[] words = laptop.getName().trim().split("\\s+");
        if (words.length >= 2) {
            // đổi vị trí từ đầu và từ cuối
            String temp = words[0];
            words[0] = words[words.length - 1];
            words[words.length - 1] = temp;
        }
        String fixedName = String.join(" ", words);
        laptop.setName(fixedName);

        // b) Đảo lại số lượng (ví dụ: 9981 → 1899)
        String quantityStr = String.valueOf(laptop.getQuantity());
        String reversedStr = new StringBuilder(quantityStr).reverse().toString();
        int fixedQuantity = Integer.parseInt(reversedStr);
        laptop.setQuantity(fixedQuantity);
    }
}
