// // [Mã câu hỏi (qCode): lFLcbkol].  [Loại bỏ ký tự đặc biệt, trùng và giữ nguyên thứ tự xuất hiện] Một chương trình server cho phép 
// kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương
//     trình client tương tác tới server sử dụng các luồng ký tự (BufferedReader/BufferedWriter) theo kịch bản dưới đây:
// // a.	Gửi một chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;7D6265E3"
// // b.	Nhận một chuỗi ngẫu nhiên từ server
// // c.	Loại bỏ ký tự đặc biệt, số, ký tự trùng và giữ nguyên thứ tự xuất hiện của ký tự. Gửi chuỗi đã được xử lý lên server.
// d.	Đóng kết nối và kết thúc chương trình

package com.mycompany.lflcbkol;

import java.io.*;
import java.net.*;
import java.util.*;

public class LFLcbkol {
    public static void main(String[] args) throws Exception {
        String host = "203.162.10.109";
        int port = 2208;
        String studentCode = "B22DCVT090";
        String qCode = "lFLcbkol";

        // Kết nối tới server
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(host, port), 5000);
        socket.setSoTimeout(5000);

        // Sử dụng luồng ký tự (character streams)
        BufferedReader reader = new BufferedReader(new InputStreamReader
        (socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter
        (socket.getOutputStream()));

        // (a) Gửi mã sinh viên và mã câu hỏi
        String request = studentCode + ";" + qCode;
        System.out.println("Sending: " + request);
        writer.write(request);
        writer.newLine(); // kết thúc dòng để server đọc được
        writer.flush();

        // (b) Nhận chuỗi ngẫu nhiên từ server
        String data = reader.readLine();
        System.out.println("Received: " + data);

        // (c) Xử lý chuỗi: loại ký tự đặc biệt, số, trùng lặp, giữ nguyên thứ tự
        String processed = processString(data);
        System.out.println("Processed: " + processed);

        // Gửi lại chuỗi kết quả cho server
        writer.write(processed);
        writer.newLine();
        writer.flush();

        // (d) Đóng kết nối
        writer.close();
        reader.close();
        socket.close();
    }

    // Hàm xử lý chuỗi
    private static String processString(String input) {
        if (input == null) return "";

        Set<Character> seen = new LinkedHashSet<>();
        StringBuilder sb = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) { // chỉ giữ chữ cái
                char lower = c; // giữ nguyên hoa/thường
                if (!seen.contains(lower)) {
                    seen.add(lower);
                    sb.append(lower);
                }
            }
        }
        return sb.toString();
    }
}
