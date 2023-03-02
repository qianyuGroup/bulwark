package com.qianyu.bulwark.common.utils;

/**
 * 通用工具类
 *
 * @author junlong.njl
 * 创建时间 2023-03-02
 */
public class CommonUtil {

    /**
     * 建立唯一key
     *
     * @param group          服务组名
     * @param className      rpc class
     * @param serviceVersion 服务版本
     * @return
     */
    public static String buildServiceKey(String group, String className, String serviceVersion) {
        return String.join("#", group, className, serviceVersion);
    }
}
