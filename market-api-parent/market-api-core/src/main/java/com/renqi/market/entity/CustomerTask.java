package com.renqi.market.entity;

import java.util.Date;

public class CustomerTask {
    private Integer taskId;

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

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public String getGoodLinkUrl() {
        return goodLinkUrl;
    }

    public void setGoodLinkUrl(String goodLinkUrl) {
        this.goodLinkUrl = goodLinkUrl;
    }

    public String getGoodTitle() {
        return goodTitle;
    }

    public void setGoodTitle(String goodTitle) {
        this.goodTitle = goodTitle;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getTotalVisitor() {
        return totalVisitor;
    }

    public void setTotalVisitor(Integer totalVisitor) {
        this.totalVisitor = totalVisitor;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getTaskBeginHour() {
        return taskBeginHour;
    }

    public void setTaskBeginHour(String taskBeginHour) {
        this.taskBeginHour = taskBeginHour;
    }

    public String getTaskEndHour() {
        return taskEndHour;
    }

    public void setTaskEndHour(String taskEndHour) {
        this.taskEndHour = taskEndHour;
    }

    public String getTaskBeginMiunte() {
        return taskBeginMiunte;
    }

    public void setTaskBeginMiunte(String taskBeginMiunte) {
        this.taskBeginMiunte = taskBeginMiunte;
    }

    public String getTaskEndMiunte() {
        return taskEndMiunte;
    }

    public void setTaskEndMiunte(String taskEndMiunte) {
        this.taskEndMiunte = taskEndMiunte;
    }

    public String getTaskSearchScope() {
        return taskSearchScope;
    }

    public void setTaskSearchScope(String taskSearchScope) {
        this.taskSearchScope = taskSearchScope;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}