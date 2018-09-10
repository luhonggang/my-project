package com.renqi.market.entity;

public class TaskTemplate {
    private Integer templateId;

    private String taskBeginBrowse;

    private String taskEndBrowse;

    private String taskBrowseSecond;

    private String requireName;

    private String taskType;

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getTaskBeginBrowse() {
        return taskBeginBrowse;
    }

    public void setTaskBeginBrowse(String taskBeginBrowse) {
        this.taskBeginBrowse = taskBeginBrowse;
    }

    public String getTaskEndBrowse() {
        return taskEndBrowse;
    }

    public void setTaskEndBrowse(String taskEndBrowse) {
        this.taskEndBrowse = taskEndBrowse;
    }

    public String getTaskBrowseSecond() {
        return taskBrowseSecond;
    }

    public void setTaskBrowseSecond(String taskBrowseSecond) {
        this.taskBrowseSecond = taskBrowseSecond;
    }

    public String getRequireName() {
        return requireName;
    }

    public void setRequireName(String requireName) {
        this.requireName = requireName;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}