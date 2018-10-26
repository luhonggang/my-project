package com.bootdo.common.domain;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerTask {

    private Integer taskId;

    private String orderNo;

    private Integer customerId;

    private String templateId;

    private Integer taskState;

    private String taskType;

    private Integer goodId;

    private String goodLinkUrl;

    private String goodTitle;

    private String storeName;

    private Integer totalVisitor;

    private Integer totalNumber;

    private String taskTime;

    private String taskBeginHour;

    private String taskEndHour;

    private String taskBeginMiunte;

    private String taskEndMiunte;

    private String taskSearchScope;

    private String taskBeginTime;

    private String taskEndTime;

    private Date createTime;

    private Date updateTime;
}