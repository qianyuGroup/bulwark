package com.qianyu.bulwark.common;

import com.qianyu.bulwark.common.annotation.RpcService;

import java.io.Serializable;

/**
 * <p>
 * </p>
 *
 * @author junlong.njl
 * 创建时间 2023-02-26
 */
@RpcService(interfaceName = "com.qianyu.MyRpcService", interfaceClass = Serializable.class)
public class MyRpcService implements Serializable{
}
