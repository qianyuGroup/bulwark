package com.qianyu.bulwark.protocol;

/**
 * bulwark的序列化接口
 *
 * @author junlong.njl
 * 创建时间 2023-02-27
 */
public interface BulwarkSerialization {

        /**
        * 序列化
        * @param obj
        * @return
        */
        <T> byte[] serialize(T obj);

        /**
        * 反序列化
        * @param data
        * @param clazz
        * @param <T>
        * @return
        */
        <T> T deserialize(byte[] data, Class<T> clazz);
}
