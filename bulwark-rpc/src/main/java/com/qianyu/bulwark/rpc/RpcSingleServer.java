package com.qianyu.bulwark.rpc;

import com.qianyu.bulwark.common.utils.AnnotationUtils;
import com.qianyu.bulwark.rpc.netty.NettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * </p>
 *
 * @author junlong.njl
 * 创建时间 2023-02-25
 */
public class RpcSingleServer extends NettyServer {
    private static Logger LOGGER = LoggerFactory.getLogger(RpcSingleServer.class);
    public RpcSingleServer(String serverAddress,String scanPackage){
        super(serverAddress);
        try {
          this.handlerMap.putAll(AnnotationUtils.RpcServerScan(scanPackage));
        }catch (Throwable t){
            LOGGER.error("RpcSingleServer init error",t);
        }
    }
}
