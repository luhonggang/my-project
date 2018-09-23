package com.renqi.market.exception;

/**
 * @author luhonggang
 * @date 2018/9/9
 * @since 1.0
 */
public abstract class GlobalException extends RuntimeException{
    private static final long serialVersionUID = 5702618461006406732L;
    public GlobalException() {}
    public GlobalException(String message) {
        super(message);
    }
    public GlobalException(String message, Throwable cause) {
        super(message, cause);
    }
    public GlobalException(Throwable cause) {
        super(cause);
    }
    public GlobalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    public abstract String getCode();
    public abstract String getErrorMessage();
}
