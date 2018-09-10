package com.renqi.market.exception;

import com.renqi.market.util.SystemCode;

/**
 * @author luhonggang
 * @version 1.8.0
 * @date 2018/9/10 16:08
 */
public class BaseResultException<T> {
    private String code;
    private String messages;
    public BaseResultException() {
    }
    public BaseResultException(String code, String messages) {
        super();
        this.code = code;
        this.messages = messages;
    }

    public BaseResultException(String code, String messages, T data) {
        super();
        this.code = code;
        this.messages = messages;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }


    public static BaseResultException ok() {
        return new BaseResultException(SystemCode.SYSTEM_SUCCESS.getCode(),SystemCode.SYSTEM_SUCCESS.getMsg());
    }
    public static BaseResultException fail(String code, String message) {
        return new BaseResultException(code, message);
    }
    public static BaseResultException faild(String code, String message) {
        return new BaseResultException(code, message);
    }
    // 返回系统未知异常
    public static BaseResultException failure() {
        return new BaseResultException(SystemCode.UNKNOW_EXCEPTION.getCode(), SystemCode.UNKNOW_EXCEPTION.getMsg());
    }

}