package com.qianyu.bulwark.common.enums;

/**
 * 序列化类型
 *
 * @author junlong.njl
 * 创建时间 2023-02-19
 */
public enum SerializeTypeEnum {
    /**
     * java原生序列化
     */
    JDK(1),

    /**
     * hessian2序列化
     */
    HESSIAN2(2),
    /**
     * protobuf序列化
     */
    PROTOBUF(3),
    /**
     * kryo序列化
     */
    KRYO(4),
    /**
     * msgpack序列化
     */
    MSGPACK(5),
    /**
     * avro序列化
     */
    AVRO(6),
    /**
     * thrift序列化
     */
    THRIFT(7),
    /**
     * fastjson序列化
     */
    FASTJSON(8),
    /**
     * xml序列化
     */
    XML(9),
    /**
     * bson序列化
     */
    BSON(10),
    /**
     * 未知
     */
    UNKNOWN(99);

    /**
     * 类型值
     */
    private int value;

    SerializeTypeEnum(int value) {
        this.value = value;
    }

    /**
     * 获取类型值
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
    public static SerializeTypeEnum valueOf(int value) {
        for (SerializeTypeEnum serializeTypeEnum : SerializeTypeEnum.values()) {
            if (serializeTypeEnum.getValue() == value) {
                return serializeTypeEnum;
            }
        }
        return SerializeTypeEnum.UNKNOWN;
    }
}
