package com.netty.in.action.samples.chapter02.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * 客户端
 *
 * @author whq46936
 * @version Id: EchoClient, v 0.1 2020/6/16 09:53 whq46936 Exp $
 */
public class EchoClient {
    /**
     * 主机
     */
    private final String host;

    /**
     * 端口号
     */
    private final int    port;

    /**
     * 默认构造器
     * 基于host/port
     *
     * @param host of type String
     * @param port of type int
     */
    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 启动
     *
     * @throws Exception when
     */
    public void start() throws Exception {
        System.out.println("client bootstrap start.");
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .remoteAddress(new InetSocketAddress(host, port))
             .handler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel ch) throws Exception {
                     ch.pipeline()
                       .addLast(new EchoClientHandler());
                 }
             });
            ChannelFuture f = b.connect()
                               .sync();
            System.err.println(this.getClass()
                                   .getName()
                               + " :::: started and connect on " + f.channel()
                                                                    .localAddress());
            f.channel()
             .closeFuture()
             .sync();
        } finally {
            group.shutdownGracefully()
                 .sync();
        }
    }

    /**
     * Method bootstrap ...
     *
     * @param host of type String
     * @param port of type int
     * @throws Exception when
     */
    public static void bootstrap(String host, int port) throws Exception {
        new EchoClient(host, port).start();
    }

    /**
     * Method main ...
     *
     * @param args of type String[]
     * @throws Exception when
     */
    public static void main(String[] args) throws Exception {
        new EchoClient("127.0.0.1", 20000).start();
    }
}