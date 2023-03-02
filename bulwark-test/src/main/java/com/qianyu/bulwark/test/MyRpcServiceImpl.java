package com.qianyu.bulwark.test;

import com.qianyu.bulwark.common.annotation.RpcService;

import java.io.Serializable;

/**
 * <p>
 * </p>
 *
 * @author junlong.njl
 * 创建时间 2023-02-26
 */
@RpcService(interfaceName = "com.qianyu.MyRpcService", interfaceClass = MyService.class)
public class MyRpcServiceImpl implements MyService{

        @Override
        public int add(int a, int b) {
            return a + b;
        }
}
