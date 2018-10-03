package com.renqi.market.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerTask {

    /**
     * 0 未执行，1 进行中 ， 2 已完成， 3 异常)
     */
    public enum TaskState{
        TASK_INCOMPLETED(0),
        TASK_CONTINUE(1),
        TASK_COMPLETED(2),
        TASK_EXCEPTION(3),
        TASK_WAITING(4);
        private Integer state;

        TaskState(Integer state){
            this.state = state;
        }

        public Integer getState() {
            return state;
        }
    }

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

    private Date createTime;

    private Date updateTime;
}