package com.qianyu.bulwark.common.enums;

/**
 * rpc调用策略
 *
 * @author junlong.njl
 * 创建时间 2023-02-19
 */
public enum RpcCallStrategyEnum {
    NORMAL(1),
    /**
     * 单向
     */
    ONEWAY(2),
    /**
     * 未知
     */
    UNKNOWN(99);
    /**
     * 策略值
     */
    private int value;

    RpcCallStrategyEnum(int value) {
        this.value = value;
    }

    /**
     * 获取策略值
     * @return
     */
    public int getValue() {
        return value;
    }

    /**
     * 根据value获取策略枚举类型
     * 查不到返回{@link RpcCallStrategyEnum#UNKNOWN}
     * @param value
     * @return
     */
    public static RpcCallStrategyEnum valueOf(int value) {
        for (RpcCallStrategyEnum rpcCallStrategyEnum : RpcCallStrategyEnum.values()) {
            if (rpcCallStrategyEnum.getValue() == value) {
                return rpcCallStrategyEnum;
            }
        }
        return RpcCallStrategyEnum.UNKNOWN;
    }
}
