package ru.gb.file.warehouse.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NioServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.socket().bind(new InetSocketAddress(45001));
        serverChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Сервер стартовал на порту 45001. Ожидаем соединения...");

        while (true) {
            selector.select(); // Блокирующий вызов, только один
            for (SelectionKey event : selector.selectedKeys()) {
                if (event.isValid()) {
                    try {
                        if (event.isAcceptable()) { // Новое соединение
                            SocketChannel socketChannel = serverChannel.accept(); // Не блокирующий
                            socketChannel.configureBlocking(false);
                            System.out.println("Подключен " + socketChannel.getRemoteAddress());
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        } else if (event.isReadable()) { // Готов к чтению
                            SocketChannel socketChannel = (SocketChannel) event.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(100);
                            int readBytes = socketChannel.read(byteBuffer);
                            if (readBytes == -1) {
                                socketChannel.close();
                            } else {
                                System.out.println(new String(byteBuffer.array()));
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("ошибка " + e.getMessage());
                    }
                }
            }
            selector.selectedKeys().clear();
        }
    }
}
