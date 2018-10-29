package com.renqi.market.common;

/**
 * 任务类型枚举
 * @author luhonggang
 * @date 2018/10/26
 * @since 1.0
 */
public enum TaskType {
    PC_ANONYMOUS_ACCESS("1","PC端匿名访问"),
    PC_REALNAME_ACCESS("2","PC端实名访问"),
    PC_DIRECT_TRAIN("3","PC直通车流量"),
    PC_JUHUASUAN_OPENS("4","PC聚划算开团提醒"),
    MB_ANONYMOUS_ACCESS("5","手机端匿名访问"),
    MB_REALNAME_ACCESS("6","手机端实名访问"),
    MB_TAMALL_APP("7","手机端天猫App流量"),
//    MB_PASSWORD_FLOW("8","手机端淘口令流量"),
    OT_JINGDONG_APP("9","其他京东App流量"),
    OT_GUESS_YOULIKE("10","猜你喜欢流量"),
    OT_MOBILE_DIRECT("11","手机直通车流量"),
    PC_COLLECTION_PURCHASE("12","PC端收藏加购"),
    MB_COLLECTION_PURCHASE("13","手机端收藏加购")
    ;
    String value;
    String typeName;

    TaskType(String value, String typeName) {
        this.value = value;
        this.typeName = typeName;
    }

    public String getValue() {
        return value;
    }

    public String getTypeName() {
        return typeName;
    }
}
