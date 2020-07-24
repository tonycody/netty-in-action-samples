package com.netty.in.action.samples.chapter04;

import com.netty.in.action.samples.chapter04.plain.client.PlainNioClient;
import com.netty.in.action.samples.chapter04.plain.client.PlainOioClient;
import com.netty.in.action.samples.chapter04.plain.server.PlainNioServer;
import com.netty.in.action.samples.chapter04.plain.server.PlainOioServer;
import org.junit.Test;

/**
 * @author whq46936
 * @version Id: PlanTests, v 0.1 2020/7/22 19:20 whq46936 Exp $
 */
public class PlanTests {

    @Test
    public void nioClientBootstrap() {
        PlainNioClient.builder()
                      .host("localhost")
                      .port(20000)
                      .build()
                      .start();
    }

    /**
     * nio服务器启动
     *
     * @throws Exception 异常
     */
    @Test
    public void nioServerBootstrap() throws Exception {
        new PlainNioServer().server(20000);
    }

    @Test
    public void oioClient() throws Exception {
        PlainOioClient.builder()
                      .host("localhost")
                      .port(20000)
                      .build()
                      .start();
    }

    /**
     * oio服务器启动
     *
     * @throws Exception 异常
     */
    @Test
    public void oioServerBootstrap() throws Exception {
        new PlainOioServer().server(20000);
    }
}