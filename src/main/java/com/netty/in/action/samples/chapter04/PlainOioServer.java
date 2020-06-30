package com.netty.in.action.samples.chapter04;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Blocking networking without Netty
 *
 * @author whq46936
 * @version Id: PlainOioServer, v 0.1 2020/6/19 08:09 whq46936 Exp $
 */
public class PlainOioServer {
    /**
     * 服务器
     *
     * @param port 港口
     * @throws Exception 异常
     */
    public void server(int port) throws Exception {
        // bind server to port
        final ServerSocket serverSocket = new ServerSocket(port);
        try {
            while (true) {
                final Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket);
                // create new thread to handler connection
                new Thread(() -> {
                    OutputStream out;
                    try {
                        out = clientSocket.getOutputStream();
                        out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));
                        out.flush();
                        clientSocket.close();
                    } catch (IOException e) {
                        try {
                            clientSocket.close();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }).start(); // start thread to begin handing
            }
        } catch (Exception e) {
            e.printStackTrace();
            serverSocket.close();
        }
    }
}