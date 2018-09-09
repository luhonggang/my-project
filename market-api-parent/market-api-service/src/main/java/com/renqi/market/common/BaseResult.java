package com.renqi.market.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luhonggang
 * @version 1.8.0
 * @date 2018/8/13 10:16
 */
@Data
public class BaseResult <T> implements Serializable {
    /**
     * 错误码
     */
    private String code;
    /**
     * 提示信息
     */
    private String msg;

    public BaseResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
