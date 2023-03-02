package com.qianyu.bulwark.common.model;

import java.io.Serializable;

/**
 * rpc传输协议模型
 *
 * @author junlong.njl
 * 创建时间 2023-02-26
 */
public class RpcProtocolModel<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 协议头信息
     */
    private RpcHeaderModel rpcHeaderModel;
    /**
     * rpc消息体
     */
    private T body;

    public RpcHeaderModel getRpcHeaderModel() {
        return rpcHeaderModel;
    }

    public void setRpcHeaderModel(RpcHeaderModel rpcHeaderModel) {
        this.rpcHeaderModel = rpcHeaderModel;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
