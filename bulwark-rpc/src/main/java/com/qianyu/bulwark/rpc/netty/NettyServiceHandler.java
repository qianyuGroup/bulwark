package com.qianyu.bulwark.rpc.netty;

import com.qianyu.bulwark.common.annotation.RpcService;
import com.qianyu.bulwark.common.enums.RpcStatusEnum;
import com.qianyu.bulwark.common.enums.RpcTypeEnum;
import com.qianyu.bulwark.common.model.*;
import com.qianyu.bulwark.protocol.exception.BulwarkSerializerException;
import com.qianyu.bulwark.common.utils.CommonUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * rpc服务端handler处理器
 *
 * @author junlong.njl
 * 创建时间 2023-02-24
 */
public class NettyServiceHandler extends SimpleChannelInboundHandler<RpcProtocolModel<RpcRequestModel>> {

    private static Logger LOGGER = LoggerFactory.getLogger(NettyServiceHandler.class);

    private final Map<String, RpcServiceModel> handlerMap;

    public NettyServiceHandler(Map<String, RpcServiceModel> handlerMap) {
        this.handlerMap = handlerMap;
    }


    /**
     * 接收消息通道
     *
     * @param channelHandlerContext
     * @param rpcProtocolModel
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocolModel<RpcRequestModel> rpcProtocolModel) throws Exception {
        //step1: 返回值msgtype先设置成response
        RpcHeaderModel rpcHeaderModel = rpcProtocolModel.getRpcHeaderModel();
        rpcHeaderModel.setMsgType((byte) RpcTypeEnum.RESPONSE.getType());

        RpcProtocolModel<RpcResponseModel> result = new RpcProtocolModel<>();
        RpcResponseModel rpcResponseModel = new RpcResponseModel();

        RpcRequestModel rpcRequestModel = rpcProtocolModel.getBody();
        //step2: 开始调用本地方法
        try {
            Object resultObj = invoke(rpcRequestModel);
            rpcResponseModel.setResult(resultObj);
            rpcResponseModel.setRpcCallStrategy(rpcRequestModel.getRpcCallStrategy());
            rpcResponseModel.setRpcCallType(rpcRequestModel.getRpcCallType());
            rpcHeaderModel.setStatus((byte) RpcStatusEnum.SUCCESS.getValue());
        } catch (Throwable t) {
            rpcHeaderModel.setStatus((byte) RpcStatusEnum.FAIL.getValue());
            rpcResponseModel.setErrorMsg(t.toString());
        }

        result.setRpcHeaderModel(rpcHeaderModel);
        result.setBody(rpcResponseModel);
        channelHandlerContext.writeAndFlush(result).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                System.out.println("结束啦啦啦啦啦啦");
            }
        });
    }

    /**
     * 根据不同代理策略调用本地函数
     *
     * @param rpcRequestModel
     * @return
     * @throws Throwable
     */
    private Object invoke(RpcRequestModel rpcRequestModel) throws Throwable {
        String serviceKey = CommonUtil.buildServiceKey(rpcRequestModel.getGroup(), rpcRequestModel.getClassName(), rpcRequestModel.getVersion());
        RpcServiceModel rpcServiceModel = handlerMap.get(serviceKey);
        Object serviceBean = rpcServiceModel.getInstance();
        RpcService rpcService = rpcServiceModel.getRpcService();
        if (serviceBean == null || rpcService == null) {
            throw new BulwarkSerializerException("service class not found");
        }

        switch (rpcService.binding().proxyType()) {
            case JDK:
                return invokeMethodWithJDK(rpcRequestModel, serviceBean);
            case CGLIB:
                return invokeMethodWithCglib(rpcRequestModel, serviceBean);
        }
        throw new BulwarkSerializerException("service proxy type not match");
    }

    /**
     * jdk反射调用方法
     *
     * @param rpcRequestModel
     * @param serviceBean
     * @return
     * @throws Throwable
     */
    private Object invokeMethodWithJDK(RpcRequestModel rpcRequestModel, Object serviceBean) throws Throwable {
        Class<?> clazz = serviceBean.getClass();
        Method method = clazz.getMethod(rpcRequestModel.getMethodName(), rpcRequestModel.getParameterTypes());
        if (method == null) {
            throw new BulwarkSerializerException("service method not found");
        }
        return method.invoke(serviceBean, rpcRequestModel.getParameters());
    }

    /**
     * cglib函数调用
     *
     * @param rpcRequestModel
     * @param serviceBean
     * @return
     * @throws Throwable
     */
    private Object invokeMethodWithCglib(RpcRequestModel rpcRequestModel, Object serviceBean) throws Throwable {
        Class<?> clazz = serviceBean.getClass();
        FastClass serviceFastClass = FastClass.create(clazz);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(rpcRequestModel.getMethodName(), rpcRequestModel.getParameterTypes());
        return serviceFastMethod.invoke(serviceBean, rpcRequestModel.getParameters());
    }
}
