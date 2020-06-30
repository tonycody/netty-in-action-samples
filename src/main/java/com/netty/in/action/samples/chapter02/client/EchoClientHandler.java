package com.netty.in.action.samples.chapter02.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author whq46936
 * @version Id: EchoClientHandler, v 0.1 2020/6/16 10:07 whq46936 Exp $
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    /**
     * Method channelActive ...
     *
     * @param ctx of type ChannelHandlerContext
     * @throws Exception when
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer(":::::::::::::::::::::::::::::::: Netty rocks!", CharsetUtil.UTF_8));
    }

    /**
     * Method channelRead0 ...
     *
     * @param ctx of type ChannelHandlerContext
     * @param msg of type ByteBuf
     * @throws Exception when
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.err.println(":::::::::::::::::::::::::::::::: Client received: " + msg.toString(CharsetUtil.UTF_8));
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