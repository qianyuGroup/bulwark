package com.qianyu.bulwark.test;

import com.qianyu.bulwark.common.annotation.RpcReference;

/**
 * <p>
 * </p>
 *
 * @author junlong.njl
 * 创建时间 2023-02-26
 */
public class MyReference {
    @RpcReference
    private MyRpcServiceImpl myRpcService;
}
