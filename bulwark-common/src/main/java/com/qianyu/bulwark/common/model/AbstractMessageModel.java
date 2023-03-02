package com.qianyu.bulwark.common.model;

import com.qianyu.bulwark.common.enums.RpcCallStrategyEnum;
import com.qianyu.bulwark.common.enums.RpcCallTypeEnum;

/**
 * 数据传输基础模型
 *
 * @author junlong.njl
 * 创建时间 2023-02-26
 */
public abstract class AbstractMessageModel {
    /**
     * rpc调用类型
     * 取值参考{@link com.qianyu.bulwark.common.enums.RpcCallTypeEnum
     */
    private int rpcCallType = RpcCallTypeEnum.SYNC.getValue();
    /**
     * rpc调用策略
     * 取值参考{@link com.qianyu.bulwark.common.enums.RpcCallStrategyEnum}
     */
    private int rpcCallStrategy = RpcCallStrategyEnum.NORMAL.getValue();

    public int getRpcCallType() {
        return rpcCallType;
    }

    public void setRpcCallType(int rpcCallType) {
        this.rpcCallType = rpcCallType;
    }

    public int getRpcCallStrategy() {
        return rpcCallStrategy;
    }

    public void setRpcCallStrategy(int rpcCallStrategy) {
        this.rpcCallStrategy = rpcCallStrategy;
    }
}
