package com.qianyu.bulwark.test;

import com.alibaba.fastjson.JSONObject;
import com.qianyu.bulwark.common.enums.RpcCallStrategyEnum;
import com.qianyu.bulwark.common.enums.RpcCallTypeEnum;
import com.qianyu.bulwark.common.enums.SerializeTypeEnum;
import com.qianyu.bulwark.common.model.RpcProtocolModel;
import com.qianyu.bulwark.common.model.RpcRequestModel;
import com.qianyu.bulwark.common.model.RpcResponseModel;
import com.qianyu.bulwark.common.utils.RpcHeaderFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * rpc服务reference处理器
 *
 * @author junlong.njl
 * 创建时间 2023-02-27
 */
public class NettyReferenceTestHandler extends SimpleChannelInboundHandler<RpcProtocolModel<RpcResponseModel>> {

    private static Logger LOGGER = LoggerFactory.getLogger(NettyReferenceTestHandler.class);

    /**
     * channel激活
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        RpcProtocolModel<RpcRequestModel> rpcProtocolModel = new RpcProtocolModel<>();
        rpcProtocolModel.setRpcHeaderModel(RpcHeaderFactory.createRpcHeader(SerializeTypeEnum.JDK.getValue()));
        RpcRequestModel rpcRequestModel = new RpcRequestModel();
        rpcRequestModel.setClassName("com.qianyu.bulwark.test.MyService");
        rpcRequestModel.setGroup("default");
        rpcRequestModel.setMethodName("add");
        rpcRequestModel.setParameters(new Object[]{1,2});
        rpcRequestModel.setParameterTypes(new Class[]{int.class, int.class});
        rpcRequestModel.setVersion("1.0.0");
        rpcRequestModel.setRpcCallType(RpcCallTypeEnum.SYNC.getValue());
        rpcRequestModel.setRpcCallStrategy(RpcCallStrategyEnum.NORMAL.getValue());
        rpcProtocolModel.setBody(rpcRequestModel);
        try {
            ctx.writeAndFlush(rpcProtocolModel);
            System.out.println("发送消息拉");
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }

    /**
     * 接收消息通道
     * @param channelHandlerContext
     * @param rpcResponseModelRpcProtocolModel
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocolModel<RpcResponseModel> rpcResponseModelRpcProtocolModel) throws Exception {

        System.out.println("接收到消息拉");
        LOGGER.info("服务端接收的消息: {}", JSONObject.toJSONString(rpcResponseModelRpcProtocolModel));
    }
}
