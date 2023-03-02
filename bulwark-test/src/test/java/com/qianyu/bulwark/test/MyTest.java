package com.qianyu.bulwark.test;

import com.qianyu.bulwark.common.enums.ProxyTypeEnum;
import com.qianyu.bulwark.common.enums.RpcCallStrategyEnum;
import com.qianyu.bulwark.common.enums.RpcCallTypeEnum;
import com.qianyu.bulwark.common.model.RpcHeaderModel;
import com.qianyu.bulwark.common.model.RpcProtocolModel;
import com.qianyu.bulwark.common.model.RpcRequestModel;
import com.qianyu.bulwark.common.utils.RpcHeaderFactory;
import com.qianyu.bulwark.rpc.RpcSingleServer;
import org.junit.Test;

/**
 * <p>
 * </p>
 *
 * @author junlong.njl
 * 创建时间 2023-02-26
 */
public class MyTest {
    @Test
    public void test1() {
        RpcSingleServer rpcSingleServer = new RpcSingleServer("127.0.0.1:27001", "com.qianyu");
        rpcSingleServer.startServer();
    }

    @Test
    public void test2() {

        RpcHeaderModel header = RpcHeaderFactory.createRpcHeader(ProxyTypeEnum.JDK.getValue());
        RpcRequestModel rpcRequestModel = new RpcRequestModel();
        rpcRequestModel.setRpcCallStrategy(RpcCallStrategyEnum.NORMAL.getValue());
        rpcRequestModel.setRpcCallType(RpcCallTypeEnum.SYNC.getValue());
        rpcRequestModel.setClassName(" com.qianyu.bulwark.test.MyRpcServiceImpl");
        rpcRequestModel.setMethodName("add");
        rpcRequestModel.setGroup("default");
        rpcRequestModel.setParameters(new Object[]{1, 2});
        rpcRequestModel.setParameterTypes(new Class[]{Integer.class, Integer.class});
        rpcRequestModel.setVersion("1.0.0");
        RpcProtocolModel<RpcRequestModel> protocol = new RpcProtocolModel<>();
        protocol.setBody(rpcRequestModel);
        protocol.setRpcHeaderModel(header);
        System.out.println(1);

    }

    @Test
    public void test3(){
     int a = 99;
     byte b = (byte) a;
        System.out.println(b);
    }
}
