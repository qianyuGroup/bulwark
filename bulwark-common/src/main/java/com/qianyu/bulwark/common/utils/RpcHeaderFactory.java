package com.qianyu.bulwark.common.utils;

import com.qianyu.bulwark.common.enums.RpcTypeEnum;
import com.qianyu.bulwark.common.model.RpcHeaderModel;

/**
 * rpc header工厂
 *
 * @author junlong.njl
 * 创建时间 2023-02-26
 */
public class RpcHeaderFactory {



    /**
     * 生成rpc header
     * //todo 测试用，后续修改
     * @return
     */
    public static RpcHeaderModel createRpcHeader(int serializationType) {
        RpcHeaderModel rpcHeaderModel = new RpcHeaderModel();
        rpcHeaderModel.setMagic(AppConstants.MAGIC);
        rpcHeaderModel.setRequestId(IdGeneratorUtils.nextId());
        rpcHeaderModel.setMsgType((byte) RpcTypeEnum.REQUEST.getType());
        rpcHeaderModel.setStatus((byte)serializationType);
        rpcHeaderModel.setSerializeType(serializationType);
        return rpcHeaderModel;
    }
}
