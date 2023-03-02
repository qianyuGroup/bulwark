package com.qianyu.bulwark.protocol.exception;

/**
 * 序列化异常
 *
 * @author junlong.njl
 * 创建时间 2023-02-27
 */
public class BulwarkSerializerException extends RuntimeException{
    /**
     * 序列化异常
     * @param message
     */
    public BulwarkSerializerException(String message) {
        super(message);
    }

    /**
     * 序列化异常
     * @param message
     * @param cause
     */
    public BulwarkSerializerException(String message, Throwable cause) {
        super(message, cause);
    }
}
