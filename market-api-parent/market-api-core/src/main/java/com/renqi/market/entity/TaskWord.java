package com.renqi.market.entity;

import java.util.Date;

public class TaskWord {
    private Integer wordId;

    private Integer taskId;

    private String wordName;

    private Integer taskVisitor;

    private Integer showNumber;

    private Date createTime;

    private Date updateTime;

    public Integer getWordId() {
        return wordId;
    }

    public void setWordId(Integer wordId) {
        this.wordId = wordId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public Integer getTaskVisitor() {
        return taskVisitor;
    }

    public void setTaskVisitor(Integer taskVisitor) {
        this.taskVisitor = taskVisitor;
    }

    public Integer getShowNumber() {
        return showNumber;
    }

    public void setShowNumber(Integer showNumber) {
        this.showNumber = showNumber;
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