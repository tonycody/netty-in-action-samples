package com.netty.in.action.samples.chapter04.server;

import com.netty.in.action.samples.chapter02.server.EchoServer;
import java.math.BigDecimal;
import org.junit.Test;

/**
 * @author whq46936
 * @version Id: NettyNioServerWriteTests, v 0.1 2020/7/6 09:18 whq46936 Exp $
 */
public class NettyNioServerWriteTests {

    /**
     * 服务器引导
     *
     * @throws Exception 异常
     */
    @Test
    public void serverBootstrap() throws Exception {
        new NettyNioServerWrite().server(20000);
    }
}