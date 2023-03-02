package com.qianyu.bulwark.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;

/**
 * <p>
 * </p>
 *
 * @author junlong.njl
 * 创建时间 2023-02-27
 */
public class RpcReferenceTest {
    @Test
    public void test1() throws Throwable {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup eventExecutors = new NioEventLoopGroup(4);
        try {
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new RpcReferenceInitializer());
            bootstrap.connect("127.0.0.1", 27001).sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Thread.sleep(20000);
            eventExecutors.shutdownGracefully();
        }
    }
}
