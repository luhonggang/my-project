package com.renqi.market.common;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @auth luhonggang
 * @date 2018/9/8
 * @since 1.0
 */
@Data
public class BaseCustomer implements Serializable{
    /**
     * 用户主键ID
     */
    private Integer customerId;
    /**
     * 用户的手机号码
     */
    private String phone;
    /**
     * 密码
     */
    private String passWord;

    private String token;

    private String expireTime;

    private Integer levelId;

    private Long shopId;

    private Date loginTime;

    private Date updateTime;


    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
