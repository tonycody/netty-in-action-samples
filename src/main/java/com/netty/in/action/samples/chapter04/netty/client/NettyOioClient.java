package com.netty.in.action.samples.chapter04.netty.client;

import com.netty.in.action.samples.chapter02.client.EchoClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioSocketChannel;
import java.net.InetSocketAddress;

/**
 * @author whq46936
 * @version Id: NettyOioClient, v 0.1 2020/7/22 19:29 whq46936 Exp $
 */
public class NettyOioClient {

    /**
     * 主机
     */
    private final String host;

    /**
     * 端口号
     */
    private final int    port;

    public NettyOioClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new OioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(OioSocketChannel.class)
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
}