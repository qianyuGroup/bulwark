package com.qianyu.bulwark.test;

import com.qianyu.bulwark.protocol.service.RpcDecoder;
import com.qianyu.bulwark.protocol.service.RpcEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * <p>
 * </p>
 *
 * @author junlong.njl
 * 创建时间 2023-02-28
 */
public class RpcReferenceInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new RpcEncoder());
        pipeline.addLast(new RpcDecoder());
        pipeline.addLast(new NettyReferenceTestHandler());

    }
}
