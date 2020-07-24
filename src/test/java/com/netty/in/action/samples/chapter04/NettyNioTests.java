package com.netty.in.action.samples.chapter04;

import com.netty.in.action.samples.chapter04.netty.client.NettyOioClient;
import org.junit.Test;

import com.netty.in.action.samples.chapter02.client.EchoClient;
import com.netty.in.action.samples.chapter04.netty.server.NettyNioServer;
import com.netty.in.action.samples.chapter04.netty.server.NettyNioServerThreadWrite;
import com.netty.in.action.samples.chapter04.netty.server.NettyNioServerWrite;
import com.netty.in.action.samples.chapter04.netty.server.NettyOioServer;

/**
 * @author whq46936
 * @version Id: NettyNioTests, v 0.1 2020/7/22 19:08 whq46936 Exp $
 */
public class NettyNioTests {

    /**
     * 客户端启动
     *
     * @throws Exception 异常
     */
    @Test
    public void clientBootstrap() throws Exception {
        EchoClient.bootstrap("localhost", 20000);
    }

    @Test
    public void nettyOioClientBootstrap() throws Exception {
        new NettyOioClient("localhost", 20000).start();
    }

    /**
     * Netty OIO 服务器启动
     *
     * @throws Exception 异常
     */
    @Test
    public void nettyOioServerBootstrap() throws Exception {
        new NettyOioServer().server(20000);
    }

    /**
     * Netty NIO 服务器启动
     *
     * @throws Exception 异常
     */
    @Test
    public void nettyNioServerBootstrap() throws Exception {
        new NettyNioServer().server(20000);
    }

    /**
     * Netty NIO Write Server
     *
     * @throws Exception 异常
     */
    @Test
    public void nettyNioWriteServerBootstrap() throws Exception {
        new NettyNioServerWrite().server(20000);
    }

    /**
     * Netty NIO Thread Write Server
     *
     * @throws Exception 异常
     */
    @Test
    public void nettyNioThreadWriteServerBootstrap() throws Exception {
        new NettyNioServerThreadWrite().server(20000);
    }
}