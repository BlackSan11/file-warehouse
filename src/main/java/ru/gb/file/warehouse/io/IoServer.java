package ru.gb.file.warehouse.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class IoServer {

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(45001)) {
            while (true) {
                Socket connection = serverSocket.accept(); // block
                System.out.println("Connected: " + connection.getRemoteSocketAddress());
                new Thread(() -> processConnection(connection)).start();
            }
        }
    }
    private static void processConnection(Socket connectionSocket) {
        try (connectionSocket;
             InputStream inputStream = connectionSocket.getInputStream()) {
            byte[] byteBuffer = new byte[1024];
            inputStream.read(byteBuffer); //blocking
            System.out.println(new String(byteBuffer));
            try (OutputStream outputStream = connectionSocket.getOutputStream()) {
                outputStream.write("All bytes read\n".getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
