package com.renqi.market.entity;

import lombok.Data;

@Data
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

    /**
     * 折扣
     */
    private Double discount;

    private String expiredTime;
}