package com.renqi.market.entity;

public class CustomerState {
    private Integer customerStateId;

    private Integer customerId;

    private Integer totalTask;

    private Integer isRecharge;

    private String expiredTime;

    public Integer getCustomerStateId() {
        return customerStateId;
    }

    public void setCustomerStateId(Integer customerStateId) {
        this.customerStateId = customerStateId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getTotalTask() {
        return totalTask;
    }

    public void setTotalTask(Integer totalTask) {
        this.totalTask = totalTask;
    }

    public Integer getIsRecharge() {
        return isRecharge;
    }

    public void setIsRecharge(Integer isRecharge) {
        this.isRecharge = isRecharge;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }
}