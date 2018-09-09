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

}
