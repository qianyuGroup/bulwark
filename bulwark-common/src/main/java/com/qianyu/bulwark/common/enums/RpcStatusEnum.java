package com.qianyu.bulwark.common.enums;

/**
 * rpc调用状态枚举
 * @author junlong.njl
 * 创建时间 2023-02-28
 */
public enum RpcStatusEnum {
    /**
     * 调用成功
     */
    SUCCESS(1),
    /**
     * 调用失败
     */
    FAIL(2),
    /**
     * 调用超时
     */
    TIMEOUT(3);

    private int value;

    RpcStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
