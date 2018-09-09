package com.renqi.market.exception;

/**
 * @author luhonggang
 * @date 2018/9/8
 * @since 1.0
 */
public class HandleTokenException extends Exception {
    private static final long serialVersionUID = -7034897190745766939L;
    private String code;
    private String msg;

    public HandleTokenException() {
    }

    public HandleTokenException(String message, String code, String msg) {
        super(message);
        this.code = code;
        this.msg = msg;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
