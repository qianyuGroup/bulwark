package com.qianyu.bulwark.rpc.netty;

import com.qianyu.bulwark.common.model.RpcServiceModel;
import com.qianyu.bulwark.protocol.service.RpcDecoder;
import com.qianyu.bulwark.protocol.service.RpcEncoder;
import com.qianyu.bulwark.rpc.service.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * netty服务实现类
 *
 * @author junlong.njl
 * 创建时间 2023-02-24
 */
public class NettyServer implements Server {
    private static Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);

    /**
     * 控制是否已启动标识位
     */
    private AtomicBoolean started = new AtomicBoolean();

    protected String host = "127.0.0.1";
    protected int port = 27000;

    /**
     * 服务地址分隔符
     */
    private static final String SPLITER = ":";
    /**
     * 服务处理器map
     */
    protected  Map<String, RpcServiceModel> handlerMap = new HashMap<>();



    /**
     * 初始化host和port
     * @param serverAddress
     */
    public NettyServer(String serverAddress){
        if(serverAddress==null || serverAddress.length()==0||!serverAddress.contains(":")){
            return;
        }
        String[] serverArr = serverAddress.split(SPLITER);
        if(serverArr.length != 2){
            return;
        }
        this.host = serverArr[0];
        this.port = Integer.parseInt(serverArr[1]);
    }


    /**
     * 开启netty服务
     */
    @Override
    public void startServer() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("bulwark-rpc-boss", true));
        NioEventLoopGroup workGroup = new NioEventLoopGroup(0, new DefaultThreadFactory("bulwark-rpc-work", true));

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new RpcDecoder())
                                    .addLast(new RpcEncoder())
                                    .addLast(new NettyServiceHandler(handlerMap));
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture channelFuture = serverBootstrap.bind(host, port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Throwable t) {
            LOGGER.error("NettyServer startServer error", t);
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
