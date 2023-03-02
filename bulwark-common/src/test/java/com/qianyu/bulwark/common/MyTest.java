package com.qianyu.bulwark.common;

import com.qianyu.bulwark.common.model.RpcServiceModel;
import com.qianyu.bulwark.common.utils.AnnotationUtils;
import org.junit.Test;

import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author junlong.njl
 * 创建时间 2023-02-20
 */
public class MyTest {
    @Test
    public void test1() {
        try {
            Map<String, RpcServiceModel> resultMap = AnnotationUtils.RpcServerScan("com.qianyu");
            System.out.println(resultMap);
        }catch (Throwable t){
            t.printStackTrace();
        }
    }

}
