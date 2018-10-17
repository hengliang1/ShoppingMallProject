package com.cdi.shoppingMall.base;

import java.io.Serializable;

/**
 * Created by jiao.zhu on 2016/12/1.
 * 数据Bean的基类。
 */

public class BaseRaiingBean implements Serializable {

    /**
     * 接口返回的提示消息
     */
    public String message;
    /**
     * 接口返回的错误码
     */
    public int code;
    /**
     * 接口返回的状态码 类似：404 200
     */
    public int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
