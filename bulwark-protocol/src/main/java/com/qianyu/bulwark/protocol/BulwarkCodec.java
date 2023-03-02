package com.qianyu.bulwark.protocol;

import com.qianyu.bulwark.protocol.service.JdkSerialization;

/**
 * bulwark的编码接口
 *
 * @author junlong.njl
 * 创建时间 2023-02-27
 */
public interface BulwarkCodec {
    /**
     * 获取序列化实现
     * @return
     */
    default BulwarkSerialization getSerialization() {
        return new JdkSerialization();
    }
}
