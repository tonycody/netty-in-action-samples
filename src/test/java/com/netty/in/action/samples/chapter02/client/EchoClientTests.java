package com.netty.in.action.samples.chapter02.client;

import org.junit.Test;

/**
 * @author whq46936
 * @version Id: EchoClientTests, v 0.1 2020/6/17 09:06 whq46936 Exp $
 */
public class EchoClientTests {
    /**
     * Method bootstrap ...
     *
     * @throws Exception when
     */
    @Test
    public void bootstrap() throws Exception {
        EchoClient.bootstrap("localhost", 20000);
    }
}