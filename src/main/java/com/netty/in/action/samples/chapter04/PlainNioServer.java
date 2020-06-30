package com.netty.in.action.samples.chapter04;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Asynchronous networking without Netty
 *
 * @author whq46936
 * @version Id: PlainNioServer, v 0.1 2020/6/22 19:39 whq46936 Exp $
 */
public class PlainNioServer {

    /**
     * 服务器
     *
     * @param port 港口
     * @throws Exception 异常
     */
    public void server(int port) throws Exception {
        System.out.println("Listening for connections on port " + port);
        //open selector that handles channels
        Selector selector = Selector.open();
        // open ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // get ServerSocket
        ServerSocket serverSocket = serverSocketChannel.socket();
        // bind server to port
        serverSocket.bind(new InetSocketAddress(port));
        // set to no-blocking
        serverSocketChannel.configureBlocking(true);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());
        while (true) {
            int n = selector.select();
            if (n > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    try {
                        // Check if event was because new client ready to get accepted
                        if (selectionKey.isAcceptable()) {
                            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                            SocketChannel       client = server.accept();
                            System.out.println("Accepted connection from " + client);
                            client.configureBlocking(false);
                            //Accept client and register it to selector
                            client.register(selector, SelectionKey.OP_WRITE, msg.duplicate());
                        }
                        // Check if event was because socket is ready to write data
                        if (selectionKey.isWritable()) {
                            SocketChannel client = (SocketChannel) selectionKey.channel();
                            ByteBuffer    buff   = (ByteBuffer) selectionKey.attachment();
                            // write data to connected client
                            while (buff.hasRemaining()) {
                                if (client.write(buff) == 0) {
                                    break;
                                }
                            }
                            // close client
                            client.close();
                        }
                    } catch (Exception e) {
                        selectionKey.cancel();
                        selectionKey.channel().close();
                    }
                }
            }
        }

    }
}