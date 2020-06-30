package com.netty.in.action.samples.chapter02.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author whq46936
 * @version Id: EchoServerHandler, v 0.1 2020/6/16 09:11 whq46936 Exp $
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * Method channelRead ...
     *
     * @param ctx of type ChannelHandlerContext
     * @param msg of type Object
     * @throws Exception when
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        //将消息记录到控制台
        System.out.println(":::::::::::::::::::::::::::::::: Server received:" + in.toString(CharsetUtil.UTF_8));
        ctx.write(in);
    }

    /**
     * Method channelReadComplete ...
     *
     * @param ctx of type ChannelHandlerContext
     * @throws Exception when
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * Method exceptionCaught ...
     *
     * @param ctx   of type ChannelHandlerContext
     * @param cause of type Throwable
     * @throws Exception when
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}