package com.renqi.market.common;

public enum TaskState {
    /**
     * 0 未执行，1 进行中 ， 2 已完成， 3 异常)
     */
    TASK_INCOMPLETED(0,"未执行"),
    TASK_CONTINUE(1,"进行中"),
    TASK_COMPLETED(2,"已完成"),
    TASK_EXCEPTION(3,"异常"),
    TASK_WAITING(4,"未知");
    private Integer state;
    private String stateName;

    TaskState(Integer state, String stateName) {
        this.state = state;
        this.stateName = stateName;
    }

    public Integer getState() {
        return state;
    }

    public String getStateName() {
        return stateName;
    }
}
