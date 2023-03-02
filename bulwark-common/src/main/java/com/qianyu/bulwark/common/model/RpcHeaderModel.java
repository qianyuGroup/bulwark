package com.qianyu.bulwark.common.model;

import java.io.Serializable;

/**
 * rpc header模型
 *
 *     +--------------------------------------------------------+
 *     | 魔数 2byte | 报文类型 1byte | 状态 1byte  | 消息id 8byte  |
 *     +--------------------------------------------------------+
 *     | 序列化类型 4byte          |      数据长度 4byte       |
 *     +--------------------------------------------------------+
 *
 *
 * 固定32字节，后续可扩展
 * @author junlong.njl
 * 创建时间 2023-02-26
 */
public class RpcHeaderModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 魔数，用来验证报文身份 2字节
     */
    private short magic;
    /**
     * 报文类型 1字节
     */
    private byte msgType;
    /**
     * 状态 1字节
     */
    private byte status;
    /**
     * 请求id 8字节
     */
    private long requestId;
    /**
     * 序列化类型
     */
    private int serializeType;
    /**
     * 传输长度 4字节
     */
    private int msgLength;

    public short getMagic() {
        return magic;
    }

    public void setMagic(short magic) {
        this.magic = magic;
    }

    public byte getMsgType() {
        return msgType;
    }

    public void setMsgType(byte msgType) {
        this.msgType = msgType;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public int getSerializeType() {
        return serializeType;
    }

    public void setSerializeType(int serializeType) {
        this.serializeType = serializeType;
    }

    public int getMsgLength() {
        return msgLength;
    }

    public void setMsgLength(int msgLength) {
        this.msgLength = msgLength;
    }
}
