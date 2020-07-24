package com.netty.in.action.samples.chapter02;

import com.netty.in.action.samples.chapter02.client.EchoClient;
import com.netty.in.action.samples.chapter02.server.EchoServer;
import org.junit.Test;

/**
 * @author whq46936
 * @version Id: EchoTests, v 0.1 2020/7/22 19:06 whq46936 Exp $
 */
public class EchoTests {

    /**
     * 客户端启动
     *
     * @throws Exception 异常
     */
    @Test
    public void clientBootstrap() throws Exception {
        EchoClient.bootstrap("localhost", 20000);
    }

    /**
     * 服务器引导
     *
     * @throws Exception 异常
     */
    @Test
    public void serverBootstrap() throws Exception {
        EchoServer.bootstrap(20000);
    }
}