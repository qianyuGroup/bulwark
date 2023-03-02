package com.qianyu.bulwark.common.model;

import com.qianyu.bulwark.common.annotation.RpcService;

/**
 * rpc服务对象
 *
 * @author junlong.njl
 * 创建时间 2023-03-03
 */
public class RpcServiceModel {
    /**
     * rpc实例
     */
    private Object instance;
    /**
     * rpc注解
     */
    private RpcService rpcService;


    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public RpcService getRpcService() {
        return rpcService;
    }

    public void setRpcService(RpcService rpcService) {
        this.rpcService = rpcService;
    }
}
