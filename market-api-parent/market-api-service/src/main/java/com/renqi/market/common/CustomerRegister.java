package com.renqi.market.common;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
/**
 * 用户注册参数类
 * @author luhonggang
 * @date 2018/9/9
 * @since 1.0
 */
@Data
public class CustomerRegister implements Serializable {
    private String userName;
    @NotNull(message="phone")
    private String phone;
    @NotNull(message="passWord不可为空")
    private String passWord;
    private String confirmPwd;
//    @NotNull(message="mobileCode不可为空")
    private String mobileCode;
    public CustomerRegister() {
    }

    public CustomerRegister(String userName, String phone, String passWord, String confirmPwd, String mobileCode) {
        this.userName = userName;
        this.phone = phone;
        this.passWord = passWord;
        this.confirmPwd = confirmPwd;
        this.mobileCode = mobileCode;
    }
}
