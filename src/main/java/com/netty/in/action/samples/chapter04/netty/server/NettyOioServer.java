package com.netty.in.action.samples.chapter04.netty.server;

import io.netty.channel.oio.OioEventLoopGroup;
import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.oio.OioServerSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * Blocking networking with Netty
 * @author whq46936
 * @version Id: NettyOioServer, v 0.1 2020/6/22 20:03 whq46936 Exp $
 */
public class NettyOioServer {
    /**
     * 服务器
     *
     * @param port 港口
     * @throws Exception 异常
     */
    public void server(int port) throws Exception {
        ByteBuf        buf   = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi!\r\n", CharsetUtil.UTF_8));
        // 事件循环组
        EventLoopGroup group = new OioEventLoopGroup();
        try {
            // 用来引导服务器配置
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 使用OIO阻塞模式
            serverBootstrap.group(group)
                           .channel(OioServerSocketChannel.class)
                           .localAddress(new InetSocketAddress(port))
                           .childHandler(new ChannelInitializer<Channel>() {
                               // 指定 ChannelInitializer 初始化 handlers
                               @Override
                               protected void initChannel(Channel ch) throws Exception {
                                   ch.pipeline()
                                     .addLast(new ChannelInboundHandlerAdapter() {
                                         @Override
                                         public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                             // 连接后，写消息到客户端，写完后便关闭连接
                                             ctx.writeAndFlush(buf.duplicate())
                                                .addListener(ChannelFutureListener.CLOSE);
                                         }
                                     });
                               }
                           });
            ChannelFuture channelFuture = serverBootstrap.bind()
                                                         .sync();
            channelFuture.channel()
                         .closeFuture()
                         .sync();
        } catch (Exception e) {
            group.shutdownGracefully();
        }
    }
}