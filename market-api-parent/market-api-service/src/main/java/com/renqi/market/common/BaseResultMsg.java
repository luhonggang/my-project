package com.renqi.market.common;
import java.io.Serializable;

/**
 * @author luhonggang
 * @version 1.8.0
 * @date 2018/8/3 11:26
 */
public class BaseResultMsg<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 错误码
     */
    private String code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 具体内容
     */
    private T data;

    public BaseResultMsg() {
    }

    public BaseResultMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResultMsg(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
