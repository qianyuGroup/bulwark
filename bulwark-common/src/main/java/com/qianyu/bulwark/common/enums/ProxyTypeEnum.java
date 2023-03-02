package com.qianyu.bulwark.common.enums;

/**
 * 代理类型
 *
 * @author junlong.njl
 * 创建时间 2023-02-19
 */
public enum ProxyTypeEnum {
    /**
     * jdk动态代理
     */
    JDK(1),
    /**
     * cglib动态代理
     */
    CGLIB(2),
    /**
     * javassist动态代理
     */
    JAVASSIST(3),
    /**
     * ByteBuddy动态代理
     */
    BYTE_BUDDY(4),
    /**
     * 未知
     */
    UNKOWN(99);

    private int value;

    ProxyTypeEnum(int value) {
        this.value = value;
    }

    /**
     * 根据value获取代理类型枚举
     * 查不到返回{@link ProxyTypeEnum#UNKOWN}
     * @param value
     * @return
     */
    public static ProxyTypeEnum valueOf(int value) {
        for (ProxyTypeEnum proxyType : ProxyTypeEnum.values()) {
            if (proxyType.getValue() == value) {
                return proxyType;
            }
        }
        return ProxyTypeEnum.UNKOWN;
    }

    public int getValue() {
        return value;
    }
}
