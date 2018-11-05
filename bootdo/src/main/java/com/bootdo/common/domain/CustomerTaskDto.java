package com.bootdo.common.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author luhonggang
 * @date 2018/11/4
 * @since 1.0
 */
@Data
public class CustomerTaskDto {
    private Integer taskId;

    private Integer taskState;

    private String taskType;

    private String taskTime;

    private Date updateTime;
}
