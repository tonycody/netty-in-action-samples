package com.netty.in.action.samples.chapter04.plain.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import lombok.Builder;

/**
 * @author whq46936
 * @version Id: PlainNioClient, v 0.1 2020/7/22 19:38 whq46936 Exp $
 */
@Builder
public class PlainNioClient {

    /**
     * 主机
     */
    private String host;

    /**
     * 端口号
     */
    private int    port;

    public void start() {
        try {
            Selector      selector      = Selector.open();
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(host, port));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            while (true) {
                int n = selector.select();
                if (n > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys()
                                                              .iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        iterator.remove();
                        if (selectionKey.isReadable()) {
                            SocketChannel sc        = (SocketChannel) selectionKey.channel();

                            // 创建一个ByteBuffer,并开辟一个1M的缓冲区
                            ByteBuffer    buffer    = ByteBuffer.allocate(1024);
                            // 读取请求码流，返回读取到的字节数
                            int           readBytes = sc.read(buffer);
                            // 读取到字节，对字节进行编解码
                            if (readBytes > 0) {
                                // 将缓冲区当前的limit设置为position=0，用于后续对缓冲区的读取操作
                                buffer.flip();
                                // 根据缓冲区可读字节数创建字节数组
                                byte[] bytes = new byte[buffer.remaining()];
                                // 将缓冲区可读字节数组复制到新建的数组中
                                buffer.get(bytes);
                                String result = new String(bytes, "UTF-8");
                                System.err.println("客户端收到消息：" + result);
                            } else if (readBytes < 0) {
                                // 链路已经关闭，释放资源
                                selectionKey.cancel();
                                sc.close();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}