package com.qianyu.bulwark.common.annotation;

import com.qianyu.bulwark.common.enums.ProxyTypeEnum;

/**
 * rpc服务注解的extra参数
 * 用于 {@link RpcService}
 * @author junlong.njl
 * 创建时间 2023-03-02
 */
public @interface RpcServiceBinding {
    /**
     * 代理类型，默认cglib
     * @return
     */
    ProxyTypeEnum proxyType() default ProxyTypeEnum.CGLIB;
}
