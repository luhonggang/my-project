package com.renqi.market.exception;

import com.renqi.market.util.SystemCode;

/**
 * @author luhonggang
 * @version 1.8.0
 * @date 2018/9/10 16:00
 */
public class NotExistTokenException extends GlobalException {
    private static final long serialVersionUID = 8867783841194312911L;

    public NotExistTokenException() {}
        @Override
    public SystemCode getCode() {
        return null;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }
}
