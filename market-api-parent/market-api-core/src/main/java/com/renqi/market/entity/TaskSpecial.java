package com.renqi.market.entity;

import java.util.Date;

public class TaskSpecial {
    private Integer taskSpecialId;

    private String taskSearchPlat;

    private Date createTime;

    public Integer getTaskSpecialId() {
        return taskSpecialId;
    }

    public void setTaskSpecialId(Integer taskSpecialId) {
        this.taskSpecialId = taskSpecialId;
    }

    public String getTaskSearchPlat() {
        return taskSearchPlat;
    }

    public void setTaskSearchPlat(String taskSearchPlat) {
        this.taskSearchPlat = taskSearchPlat;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}