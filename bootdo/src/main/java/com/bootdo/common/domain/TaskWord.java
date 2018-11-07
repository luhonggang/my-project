package com.bootdo.common.domain;

import lombok.Data;

import java.util.Date;

@Data
public class TaskWord {
    private Integer wordId;

    private Integer taskId;

    private String wordName;

    private Integer taskVisitor;

    private Integer showNumber;

    private Date createTime;

    private Date updateTime;

}