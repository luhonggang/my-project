package com.renqi.market.util;

/**
 * 系统返回码定义
 * @author luhonggang
 * @version 1.8.0
 * @date 2018/8/3 11:36
 */
@SuppressWarnings("all")
public enum SystemCode {
    SYSTEM_SUCCESS("200","成功"),
    SYSTEM_SUCCESS_LOGIN("200","登录成功"),
    SYSTEM_LOGIN_ERROR("201","您好，请您先注册后再登录"),
    PHONE_NUMBER_ERROR("400","手机号码格式有误"),
    EXPIRED_TOKEN("401","token失效"),
    TOKEN_IS_EMPTY("402","token为空"),
    TOKEN_IS_ERROR("403","token非法"),
    PASS_WORD_ERROR("405","输入的密码有误"),
    GET_MOBILE_CODE("406","验证获取成功"),
    MOBILE_IS_ERROR("407","参数为空"),
    MOBILE_IS_REGISTER("408","手机号码已经注册过,请更换手机号"),
    MOBILE_IS_EXIST("409","验证码已经发送过,2分钟内不可重复发送"),
    MOBILE_SEND_ERROR("410","验证码发送失败,请稍后再试"),
    INPUT_CODE_ERROR("411","验证码输入有误"),
    MOBILE_EXPIRED("412","验证码失效,请重新发送验证码"),
    PARAM_VALID_EXCEPTION("413","参数校验异常"),
    TASK_TYPE_EXCEPTION("414","任务类型异常"),
    SYSTEM_ERROR("500","系统异常"),
    UNKNOW_EXCEPTION("500","未知异常"),
    GOOD_LINKURL_ERROR("501", "宝贝链接有误");


    private String code;
    private String msg;

    SystemCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
