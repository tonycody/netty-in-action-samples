package com.netty.in.action.samples.chapter04.server;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * @author whq46936
 * @version Id: NettyNioServerWrite, v 0.1 2020/7/6 09:31 whq46936 Exp $
 */
public class NettyNioServerWrite {

    /**
     * 服务器
     *
     * @param port 港口
     * @throws Exception 异常
     */
    public void server(int port) throws Exception {
        ByteBuf           buf    = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi!\r\n", CharsetUtil.UTF_8));
        ByteBuf           newBuf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
        // 事件循环组
        NioEventLoopGroup group  = new NioEventLoopGroup();

        try {
            // 用来引导服务器配置
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 使用NIO异步模式
            serverBootstrap.group(group)
                           .channel(NioServerSocketChannel.class)
                           .localAddress(new InetSocketAddress(port))
                           .childHandler(new ChannelInitializer<Channel>() {
                               // 指定 ChannelInitializer 初始化 handlers
                               @Override
                               protected void initChannel(Channel ch) throws Exception {
                                   // 添加一个"入站"handler到ChannelPipeline
                                   ch.pipeline()
                                     .addLast(new ChannelInboundHandlerAdapter() {
                                         @Override
                                         public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                             // 连接后，写消息到客户端，写完后便关闭连接
                                             ctx.writeAndFlush(buf.duplicate())
                                                .addListener(ChannelFutureListener.CLOSE);
                                         }
                                     });
                                   ChannelFuture cf = ch.write(newBuf);
                                   cf.addListener(new ChannelFutureListener() {
                                       @Override
                                       public void operationComplete(ChannelFuture future) throws Exception {
                                           if (future.isSuccess()) {
                                               System.out.println(".Write successful.");
                                           } else {
                                               System.err.println("。Write error.");
                                               future.cause()
                                                     .printStackTrace();
                                           }
                                       }
                                   });
                               }
                           });

            // 绑定服务器接受连接
            ChannelFuture channelFuture = serverBootstrap.bind()
                                                         .sync();
            channelFuture.channel()
                         .closeFuture()
                         .sync();
        } catch (Exception e) {
            // 释放所有资源
            group.shutdownGracefully();
        }
    }
}