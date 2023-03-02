package com.qianyu.bulwark.protocol.service;

import com.qianyu.bulwark.protocol.BulwarkSerialization;
import com.qianyu.bulwark.protocol.exception.BulwarkSerializerException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * jdk序列化
 *
 * @author junlong.njl
 * 创建时间 2023-02-27
 */
public class JdkSerialization implements BulwarkSerialization {
    /**
     * jdk序列化
     *
     * @param obj
     * @param <T>
     * @return
     */
    @Override
    public <T> byte[] serialize(T obj) {
        if (obj == null) {
            throw new BulwarkSerializerException("serialize obj is null");
        }
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutputStream objOut = new ObjectOutputStream(byteOut)) {
            objOut.writeObject(obj);
            return byteOut.toByteArray();
        } catch (Throwable t) {
            throw new BulwarkSerializerException("jdk serialize error", t);
        }
    }

    /**
     * jdk反序列化
     *
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        if (data == null || clazz == null) {
            throw new BulwarkSerializerException("deserialize data is null");
        }
        try (ByteArrayInputStream byteIn = new ByteArrayInputStream(data);
             ObjectInputStream objIn = new ObjectInputStream(byteIn)) {
            return (T) objIn.readObject();
        } catch (Throwable t) {
            throw new BulwarkSerializerException("jdk deserialize error", t);
        }
    }
}
