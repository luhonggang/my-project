package com.bootdo.common.domain;

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

    /**
     * 用户的手机号码
     */
    private String phone;

    private String timeScope;

    private String miunteScope;

    private String wordList;

    private String taskTypeName;

    private String bowerTime;

    private Integer taskId;

    private String orderNo;

    private Double useMoney;

    private Double useDiscount;

    private Integer customerId;

    private String templateId;

    private String templateName;

    private Integer taskState;

    private String taskType;

    private Long goodId;

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

    private String createTime;

//    private String createStrTime;

    private Date updateTime;
}