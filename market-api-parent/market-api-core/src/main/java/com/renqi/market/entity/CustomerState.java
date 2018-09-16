package com.renqi.market.entity;

public class CustomerState {
    /**
     * 客户状态信息枚举
     */
    public enum State{
        IS_RECHARGE_TRUE(1),
        IS_RECHARGE_FALSE(0);
        private Integer state;

        State(Integer state){
            this.state = state;
        }

        public Integer getState() {
            return state;
        }
    }
    private Integer customerStateId;

    private Integer totalTask;

    private Double totalMoney;

    private Integer isRecharge;

    private String expiredTime;

    public Integer getCustomerStateId() {
        return customerStateId;
    }

    public void setCustomerStateId(Integer customerStateId) {
        this.customerStateId = customerStateId;
    }


    public Integer getTotalTask() {
        return totalTask;
    }

    public void setTotalTask(Integer totalTask) {
        this.totalTask = totalTask;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
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