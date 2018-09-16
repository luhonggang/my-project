package com.renqi.market.common;

import java.io.Serializable;

/**
 * @author luhonggang
 * @date 2018/9/15
 * @since 1.0
 */
public class CustomerParamDto implements Serializable {
    /**
     * 客户ID
     */
    private Integer customerId;

    /**
     * 输入的关键词
     */
    private String wordName;
    /**
     * 任务的类型
     */
    private String taskType;

    /**
     * 任务的状态
     */
    private String taskState;

    /**
     * 任务投放开始时间
     */
    private String beginTime;

    /**
     * 任务投放结束时间
     */
    private String endTime;
}
