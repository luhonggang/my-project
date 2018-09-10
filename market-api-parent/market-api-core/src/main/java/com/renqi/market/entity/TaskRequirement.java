package com.renqi.market.entity;

import java.util.Date;

public class TaskRequirement {
    private Integer requireRelId;

    private Integer taskId;

    private Integer tempalteId;

    private String templateType;

    private Date createTime;

    public Integer getRequireRelId() {
        return requireRelId;
    }

    public void setRequireRelId(Integer requireRelId) {
        this.requireRelId = requireRelId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getTempalteId() {
        return tempalteId;
    }

    public void setTempalteId(Integer tempalteId) {
        this.tempalteId = tempalteId;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}