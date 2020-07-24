package com.netty.in.action.samples.chapter02.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 服务器端
 *
 * @author whq46936
 * @version Id: EchoServer, v 0.1 2020/6/16 08:54 whq46936 Exp $
 */
public class EchoServer {
    /**
     * 端口号
     */
    private final int port;

    /**
     * 默认构造器
     * 通过指定端口号来初始化类实例
     *
     * @param port of type int
     */
    public EchoServer(int port) {
        this.port = port;
    }

    /**
     * 启动服务器
     *
     * @throws Exception when
     */
    private void start() throws Exception {
        System.out.println("server bootstrap successful.");
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // create ServerBootstrap instance
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
             .channel(NioServerSocketChannel.class)
             .localAddress(port)
             .childHandler(new ChannelInitializer<Channel>() {
                 /**
                  * Method initChannel ...
                  *
                  * @param ch of type Channel
                  * @throws Exception when
                  */
                 @Override
                 public void initChannel(Channel ch) throws Exception {
                     ch.pipeline()
                       .addLast(new EchoServerHandler());
                 }
             });
            ChannelFuture f = b.bind()
                               .sync();
            System.err.println(this.getClass()
                                   .getName()
                               + " :::::::::::::::::::::::::::::::: started and listen on " + f.channel()
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
     * @param port of type int
     * @throws Exception when
     */
    public static void bootstrap(int port) throws Exception {
        new EchoServer(port).start();
    }

    /**
     * Method main ...
     *
     * @param args of type String[]
     * @throws Exception when
     */
    public static void main(String[] args) throws Exception {
        new EchoServer(20000).start();
    }
}