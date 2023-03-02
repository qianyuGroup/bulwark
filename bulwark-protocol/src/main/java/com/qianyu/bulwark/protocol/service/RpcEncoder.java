package com.qianyu.bulwark.protocol.service;

import com.qianyu.bulwark.common.model.RpcHeaderModel;
import com.qianyu.bulwark.common.model.RpcProtocolModel;
import com.qianyu.bulwark.common.model.RpcRequestModel;
import com.qianyu.bulwark.protocol.BulwarkCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * bulwark的编码接口
 *
 * @author junlong.njl
 * 创建时间 2023-02-27
 */
public class RpcEncoder extends MessageToByteEncoder<RpcProtocolModel<Object>> implements BulwarkCodec {
    /**
     * 将交互信息编码成字节流
     * @param channelHandlerContext
     * @param objectRpcProtocolModel
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcProtocolModel<Object> objectRpcProtocolModel, ByteBuf byteBuf) throws Exception {
        System.out.println("encode!!");
        RpcHeaderModel rpcHeaderModel = objectRpcProtocolModel.getRpcHeaderModel();
        byteBuf.writeShort(rpcHeaderModel.getMagic());
        byteBuf.writeByte(rpcHeaderModel.getMsgType());
        byteBuf.writeByte(rpcHeaderModel.getStatus());
        byteBuf.writeLong(rpcHeaderModel.getRequestId());
        int serializeType = rpcHeaderModel.getSerializeType();
        byteBuf.writeByte(serializeType);
        byte[] serialize = getSerialization().serialize(objectRpcProtocolModel.getBody());
        byteBuf.writeInt(serialize.length);
        byteBuf.writeBytes(serialize);

    }
}
