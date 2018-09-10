package com.renqi.market.entity;

import java.util.Date;

public class CustomerLevel {
    private Integer levelId;

    private String levelName;

    private String levelDesc;

    private Double money;

    private Date expiriedTime;

    private Date createTime;

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelDesc() {
        return levelDesc;
    }

    public void setLevelDesc(String levelDesc) {
        this.levelDesc = levelDesc;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Date getExpiriedTime() {
        return expiriedTime;
    }

    public void setExpiriedTime(Date expiriedTime) {
        this.expiriedTime = expiriedTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}