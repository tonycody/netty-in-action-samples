package com.netty.in.action.samples.chapter02.server;

import org.junit.Test;

/**
 * @author whq46936
 * @version Id: EchoServerTests, v 0.1 2020/6/17 09:06 whq46936 Exp $
 */
public class EchoServerTests {
    /**
     * Method serverBootstrap ...
     */
    @Test
    public void serverBootstrap() throws Exception {
        EchoServer.bootstrap(20000);
    }
}