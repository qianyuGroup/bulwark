package com.qianyu.bulwark.common.enums;

/**
 * <p>
 * </p>
 *
 * @author junlong.njl
 * 创建时间 2023-02-26
 */
public enum RpcTypeEnum {
    /**
     * 请求消息
     */
    REQUEST(1),
    /**
     * 响应消息
     */
    RESPONSE(2),
    /**
     * 心跳消息
     */
    HEARTBEAT(3),
    /**
     * 无效消息
     */
    UNKOWN(99)
    ;

    private final int value;

    RpcTypeEnum(int value) {
        this.value = value;
    }

    /**
     * 根据value获取消息枚举类型
     * 查不到返回{@link RpcTypeEnum#UNKOWN}
     * @param value
     * @return
     */
    public static RpcTypeEnum valueOf(int value) {
        for (RpcTypeEnum rpcType : RpcTypeEnum.values()) {
            if (rpcType.getType() == value) {
                return rpcType;
            }
        }
        return RpcTypeEnum.UNKOWN;
    }

    /**
     * 获取消息类型value
     * @return
     */
    public int getType() {
        return value;
    }


}
