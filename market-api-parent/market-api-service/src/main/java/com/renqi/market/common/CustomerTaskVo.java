package com.renqi.market.common;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author luhonggang
 * @date 2018/9/15
 * @since 1.0
 */
@Data
public class CustomerTaskVo {
    /**
     * 任务主键
     */
    private Integer taskId;
    /**
     * 定单编号
     */
    private String orderNo;
    /**
     * 客户主键
     */
    @NotNull(message = "客户主键不能为空")
    private Integer customerId;
    /**
     * 宝贝链接
     */
    @NotBlank(message = "宝贝链接不能为空")
    private String goodLinkUrl;

    /**
     * 关键词集合
     */
    @NotNull(message = "关键词集合")
    private List<TaskWordDto> wordDtoList;
    /**
     * 宝贝标题
     */
    private String goodTitle;

    /**
     * 店铺旺旺名
     */
    private String storeName;
    /**
     * 任务的状态
     */
    private Integer taskState;
    /**
     * 任务类型（PC端任务或手机端任务）
     */
    @NotBlank(message = "任务类型不能为空")
    private String taskType;
    /**
     * 总的访客数量
     */
    @NotNull(message = "总的访客数量不能0")
    private Integer totalVisitor;

    /**
     * 总的展现数量
     */
    @NotNull(message = "总的展现数不能0")
    private Integer totalNumber;

    /**
     * 任务投放时间
     */
    @NotBlank(message = "任务投放时间不能为空")
    private String taskTime;

    /**
     * 时段 开始小时
     */
    @NotBlank(message = "时段开始小时不能为空")
    private String taskBeginHour;

    /**
     * 时段 结束小时
     */
    @NotBlank(message = "时段结束小时不能为空")
    private String taskEndHour;

    /**
     * 任务发布的开始分钟
     */
    @NotBlank(message = "任务发布的开始分钟不能为空")
    private String taskBeginMiunte;

    /**
     * 任务发布的结束分钟
     */
    @NotBlank(message = "任务发布结束分钟不能为空")
    private String taskEndMiunte;

    /**
     * 搜索的范围
     */
    @NotBlank(message = "搜索范围不能为空")
    private String taskSearchScope;

    /**
     * 模板ID组合 浏览控制
     * 浏览时长 在task_template表中获取
     * task_begin_browse  浏览时长开始秒
     * task_end_browse    浏览时长结束秒
     */
    @NotNull( message = "浏览控制 参数不能为空")
    private String templateId;
    /**
     * 浏览总时长
     */
    private String browerTotalTime;

    private List<TaskTemplateVo> templateList;

    private String browerOneName;

    private String browerTwoName;

    private String browerThreeName;

    private String browerFourName;



}
