package com.qianyu.bulwark.protocol.service;

import com.qianyu.bulwark.common.enums.RpcTypeEnum;
import com.qianyu.bulwark.common.model.RpcHeaderModel;
import com.qianyu.bulwark.common.model.RpcProtocolModel;
import com.qianyu.bulwark.common.model.RpcRequestModel;
import com.qianyu.bulwark.common.model.RpcResponseModel;
import com.qianyu.bulwark.protocol.BulwarkCodec;
import com.qianyu.bulwark.protocol.BulwarkSerialization;
import com.qianyu.bulwark.protocol.exception.BulwarkSerializerException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import static com.qianyu.bulwark.common.utils.AppConstants.MAGIC;

/**
 * bulwark的解码接口
 *
 * @author junlong.njl
 * 创建时间 2023-02-27
 */
public class RpcDecoder extends ByteToMessageDecoder implements BulwarkCodec {
    /**
     * 将字节流解码成交互信息
     *
     * @param channelHandlerContext
     * @param byteBuf
     * @param list
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("decode!!");
        //长度校验
        int i = byteBuf.readableBytes();
        System.out.println("长度" + i);
        //保存指针
        byteBuf.markReaderIndex();

        short magic = byteBuf.readShort();
        //校验魔数
        if (magic != MAGIC) {
            throw new BulwarkSerializerException("magic number is not correct");
        }
        byte msgType = byteBuf.readByte();
        byte status = byteBuf.readByte();
        long requestId = byteBuf.readLong();
        byte serializeType = byteBuf.readByte();
        int serializeSize = byteBuf.readInt();
        //校验数据长度
        if (byteBuf.readableBytes() < serializeSize) {
            byteBuf.resetReaderIndex();
            return;
        }

        byte[] dataBytes = new byte[serializeSize];
        byteBuf.readBytes(dataBytes);
        RpcTypeEnum rpcType = RpcTypeEnum.valueOf(msgType);
        if (rpcType == RpcTypeEnum.UNKOWN) {
            return;
        }

        RpcHeaderModel rpcHeaderModel = new RpcHeaderModel();
        rpcHeaderModel.setMagic(magic);
        rpcHeaderModel.setStatus(status);
        rpcHeaderModel.setMsgType(msgType);
        rpcHeaderModel.setRequestId(requestId);
        rpcHeaderModel.setSerializeType(serializeType);
        rpcHeaderModel.setMsgLength(serializeSize);

        BulwarkSerialization serialization = getSerialization();
        switch (rpcType) {
            case REQUEST:
                RpcRequestModel rpcRequestModel = serialization.deserialize(dataBytes, RpcRequestModel.class);
                if (rpcRequestModel != null) {
                    RpcProtocolModel<RpcRequestModel> rpcProtocolModel = new RpcProtocolModel<>();
                    rpcProtocolModel.setRpcHeaderModel(rpcHeaderModel);
                    rpcProtocolModel.setBody(rpcRequestModel);
                    list.add(rpcProtocolModel);
                }
                break;
            case RESPONSE:
                RpcResponseModel rpcResponseModel = serialization.deserialize(dataBytes, RpcResponseModel.class);
                if(rpcResponseModel != null){
                    RpcProtocolModel<RpcResponseModel> rpcProtocolModel = new RpcProtocolModel<>();
                    rpcProtocolModel.setRpcHeaderModel(rpcHeaderModel);
                    rpcProtocolModel.setBody(rpcResponseModel);
                    list.add(rpcProtocolModel);
                }
        }

    }
}
