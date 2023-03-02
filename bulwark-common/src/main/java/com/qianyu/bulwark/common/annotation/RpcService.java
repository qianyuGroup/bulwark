package com.qianyu.bulwark.common.annotation;

import java.lang.annotation.*;

/**
 * 服务提供者注解,使用时需要标注在接口实现上
 * {@link RpcService#interfaceClass()} 与 {@link RpcService#interfaceName()} 尽量填写
 * 如果不填写，服务启动时会尝试搜索当前标注的class以及class实现的接口，如果找不到或者当前class实现多个接口，则服务会注册失败
 * @author junlong.njl
 * 创建时间 2023-02-19
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface RpcService {
    /**
     * rpc服务接口class
     *
     * @return
     */
    Class<?> interfaceClass() ;

    /**
     * rpc服务接口名称
     *
     * @return
     */
    String interfaceName() ;

    /**
     * rpc服务版本号
     */
    String version() default "1.0.0";

    /**
     * rpc服务分组
     */
    String group() default "default";

    /**
     * extra参数
     * @return
     */
    RpcServiceBinding binding() default @RpcServiceBinding;
}
