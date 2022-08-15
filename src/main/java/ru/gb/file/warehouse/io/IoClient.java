package ru.gb.file.warehouse.io;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class IoClient {

    public static void main(String[] args) throws IOException {
        try (Socket clientSocket = new Socket("localhost", 45001);
             OutputStream clientSocket1 = clientSocket.getOutputStream()) {
            clientSocket1.write("Hello from java IO client\n".getBytes(StandardCharsets.UTF_8));
        }
    }

    public static void main2(String[] args) throws IOException {
        List<Socket> sockets = new ArrayList<>();
        for (int i = 0; i < 1500; i++) {
            Socket clientSocket = new Socket("localhost", 45001);
            sockets.add(clientSocket);
        }
        System.out.println(sockets);
        while (true) {
        }
    }

}
