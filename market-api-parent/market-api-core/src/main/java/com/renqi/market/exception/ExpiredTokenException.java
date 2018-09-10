package com.renqi.market.exception;

import com.renqi.market.util.SystemCode;

/**
 * @author luhonggang
 * @version 1.8.0
 * @date 2018/9/10 16:05
 */
public class ExpiredTokenException extends GlobalException {

    private static final long serialVersionUID = -7051836414685780479L;

    @Override
    public SystemCode getCode() {
        return SystemCode.EXPIRED_TOKEN;
    }

    @Override
    public String getErrorMessage() {
        return String.format(SystemCode.EXPIRED_TOKEN.getMsg());
    }
}
