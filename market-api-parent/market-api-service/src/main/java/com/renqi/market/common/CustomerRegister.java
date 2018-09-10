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
    @NotNull(message="mobile")
    private String mobile;
    @NotNull(message="passWord不可为空")
    private String passWord;
//    @NotNull(message="mobileCode不可为空")
    private String mobileCode;

    public CustomerRegister() {
    }

    public CustomerRegister(String userName, @NotNull String mobile, @NotNull String passWord, String mobileCode) {
        this.userName = userName;
        this.mobile = mobile;
        this.passWord = passWord;
        this.mobileCode = mobileCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @NotNull
    public String getMobile() {
        return mobile;
    }

    public void setMobile(@NotNull String mobile) {
        this.mobile = mobile;
    }

    @NotNull
    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(@NotNull String passWord) {
        this.passWord = passWord;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }
}
