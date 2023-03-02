package com.qianyu.bulwark.common.annotation;

import com.qianyu.bulwark.common.utils.AppConstants;

import java.lang.annotation.*;

/**
 * 服务消费者注解,使用时需要标注在字段上
 *
 * @author junlong.njl
 * 创建时间 2023-02-19
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface RpcReference {
    /**
     * rpc服务版本号
     */
    String version() default "1.0.0";

    /**
     * rpc服务分组
     */
    String group() default "default";

//    /**
//     * 注册中心类型,默认zookeeper
//     * 取值参考 {@link RegistryTypeEnum}
//     *
//     * @return
//     */
//    String registryType() default "ZOOKEEPER";

//    /**
//     * 注册中心地址
//     *
//     * @return
//     */
//    String registryAddress() default "";

    /**
     * 负载均衡类型
     * 取值参考 {@link com.qianyu.bulwark.common.enums.LoadBalanceTypeEnum}
     */
    String loadBalanceType() default "CONSISTENT_HASH";

    /**
     * 序列化类型
     * 取值参考 {@link com.qianyu.bulwark.common.enums.SerializeTypeEnum}
     */
    String serializeType() default "HESSIAN2";

    /**
     * 超时时间,单位毫秒
     * 默认值参考 {@link AppConstants#RPC_DEFAULT_TIMEOUT}
     * @return
     */
    long timeout() default AppConstants.RPC_DEFAULT_TIMEOUT;

    /**
     * 调用类型
     * 取值参考 {@link com.qianyu.bulwark.common.enums.RpcCallTypeEnum}
     * @return
     */
    String callType() default "SYNC";

    /**
     * 代理类型
     * @return
     */
    String proxyType() default "JAVASSIST";
}
