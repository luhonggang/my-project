package com.renqi.market.common;

/**
 * @author luhonggang
 * @date 2018/11/4
 * @since 1.0
 */

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户发布任务实体参数类
 * @author luhonggang
 * @date 2018/9/15
 * @since 1.0
 */
@Data
public class CustomerTaskDto implements Serializable {

    public CustomerTaskDto() {
    }

    private Integer totalTask;

    private Double totalMoney;

    private Integer taskId;

    private String orderNo;

    private Double useMoney;

    private Double useDiscount;
    /**
     * 客户主键
     */
    @NotNull(message = "客户主键不能为空")
    private Integer customerId;

    /**
     * 客户状态主键
     */
    @NotNull(message = "客户状态主键ID不可为空")
    private Integer customerStateId;

    /**
     * 客户状态主键
     */
    @NotNull(message = "客户状态主键ID不可为空")
    private Double accountMoney;

    /**
     * 宝贝链接
     */
    @NotBlank(message = "宝贝链接不能为空")
    private String goodLinkUrl;
    /**
     * 宝贝ID
     */
    private Long goodId;
    /**
     * 关键词集合
     */
    //@NotNull(message = "关键词集合")
    private String wordList;
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
    @NotNull(message = "总的访客数量不能是0")
    private String totalVisitor;

    /**
     * 总的展现数量
     */
    @NotNull(message = "总的展现数不能是0")
    private String totalNumber;

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
    @NotNull(message = "浏览控制 参数不能为空 (参数格式 5,6,7)")
    private String templateId;
//    private List<String> templateList;

    private Date createTime;

    private Date updateTime;

}