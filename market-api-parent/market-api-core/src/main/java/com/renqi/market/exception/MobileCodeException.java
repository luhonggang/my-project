package com.renqi.market.exception;

import com.renqi.market.util.SystemCode;

/**
 * @author luhonggang
 * @date 2018/9/9
 * @since 1.0
 */
public class MobileCodeException extends GlobalException {
    private static final long serialVersionUID = 5702618461006406732L;
    public MobileCodeException() {}

    @Override
    public SystemCode getCode() {
        return null;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }
}
