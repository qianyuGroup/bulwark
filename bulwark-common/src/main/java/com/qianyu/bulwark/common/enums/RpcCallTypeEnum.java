package com.qianyu.bulwark.common.enums;

/**
 * rpc调用类型
 *
 * @author junlong.njl
 * 创建时间 2023-02-19
 */
public enum RpcCallTypeEnum {
    /**
     * 同步
     */
    SYNC(1),
    /**
     * 异步
     */
    ASYNC(2),
    /**
     * 未知
     */
    UNKNOWN(99);

    /**
     * 类型值
     */
    private int value;

    RpcCallTypeEnum(int value) {
        this.value = value;
    }

    /**
     * 获取类型值
     * @return
     */
    public int getValue() {
        return value;
    }

    /**
     * 根据value获取类型枚举类型
     * 查不到返回{@link RpcCallTypeEnum#UNKNOWN}
     * @param value
     * @return
     */
    public static RpcCallTypeEnum valueOf(int value) {
        for (RpcCallTypeEnum rpcCallTypeEnum : RpcCallTypeEnum.values()) {
            if (rpcCallTypeEnum.getValue() == value) {
                return rpcCallTypeEnum;
            }
        }
        return RpcCallTypeEnum.UNKNOWN;
    }

}
