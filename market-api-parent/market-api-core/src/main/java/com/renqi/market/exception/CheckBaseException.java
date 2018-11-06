package com.renqi.market.exception;

import com.renqi.market.common.BaseResult;

/**
 * @author luhonggang
 * @date 2018/11/3
 * @since 1.0
 */
public class CheckBaseException extends RuntimeException {
    private static final long serialVersionUID = 4636592210364844533L;
    private String  code;
    private String subcode;
    private String message;
    private BaseResult msg;
    public String getSubcode() {
        return subcode;
    }
    public void setSubcode(String subcode) {
        this.subcode = subcode;
    }
    public String getcode() {
        return code;
    }
    public void setcode(String code) {
        this.code = code;
    }
    public String getmessage() {
        return message;
    }
    public void setmessage(String message) {
        this.message = message;
    }
//    public CheckBaseException(String code, String subcode, String message) {
//        super(message);
//        this.code = code;
//        this.subcode = subcode;
//        this.message = message;
//    }
    public CheckBaseException(BaseResult msg,String code, String message) {
        super(message);
        this.msg = msg;
        this.code = code;
        this.message = message;
    }
    public CheckBaseException(String code) {
        super();
        this.code = code;
    }
    public CheckBaseException() {
        super();
    }

}
