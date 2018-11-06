package com.bootdo.common.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author luhonggang
 * @date 2018/11/4
 * @since 1.0
 */
@Data
public class CustomerInfoDto {
    private Integer customerId;
    private Integer stateId;
    private String phone;
    private Double discount;
    private Integer isRecharge;
    private String expiredTime;
    private String levelId;
    private String levelName;
    private String levelDesc;
    private Double totalMoney;
    private Double useMoney;
    private Date updateTime;
}
