package com.qianyu.bulwark.common.model;

import java.io.Serializable;

/**
 * rpc响应模型
 *
 * @author junlong.njl
 * 创建时间 2023-02-26
 */
public class RpcResponseModel extends AbstractMessageModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 响应结果
     */
    private Object result;
    /**
     * 异常信息
     */
    private String errorMsg;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
