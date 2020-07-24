package com.netty.in.action.samples.chapter04.plain.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import lombok.Builder;

/**
 * @author whq46936
 * @version Id: PlainOioClient, v 0.1 2020/7/22 19:39 whq46936 Exp $
 */
@Builder
public class PlainOioClient {
    /**
     * 主机
     */
    private String host;

    /**
     * 端口号
     */
    private int    port;

    /**
     * 开始
     *
     * @throws IOException ioexception
     */
    public void start() throws IOException {
        try {
            // bind server to port
            Socket socket = new Socket(host, port);
            System.out.println("Accepted connection from " + socket);
            // create new thread to handler connection
            new Thread(() -> {
                try {
                    BufferedReader in    = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String         reply = in.readLine();
                    System.err.println(reply);
                    socket.close();
                } catch (IOException e) {
                    try {
                        socket.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}