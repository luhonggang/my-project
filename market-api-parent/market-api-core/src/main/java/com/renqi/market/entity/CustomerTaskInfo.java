package com.renqi.market.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户登录后展现用户个人的信息总览详情
 * @author luhonggang
 * @date 2018/9/15
 * @since 1.0
 */
@Data
public class CustomerTaskInfo implements Serializable {
    private Integer customerId;
    private String phone;
    private CustomerState customerState;
    private CustomerLevel customerLevel;
    private CustomerShop customerShop;
    private List<CustomerTask> taskList;
    private Integer totalTask;
    private Integer taskIsDoing;
    private Integer taskIsComplete;
    private Integer taskIsException;

    public CustomerTaskInfo() {

    }

    public CustomerTaskInfo(Integer customerId, String phone, CustomerState customerState, CustomerLevel customerLevel, CustomerShop customerShop, List<CustomerTask> taskList, Integer totalTask, Integer taskIsDoing, Integer taskIsComplete, Integer taskIsException) {
        this.customerId = customerId;
        this.phone = phone;
        this.customerState = customerState;
        this.customerLevel = customerLevel;
        this.customerShop = customerShop;
        this.taskList = taskList;
        this.totalTask = totalTask;
        this.taskIsDoing = taskIsDoing;
        this.taskIsComplete = taskIsComplete;
        this.taskIsException = taskIsException;
    }
}
