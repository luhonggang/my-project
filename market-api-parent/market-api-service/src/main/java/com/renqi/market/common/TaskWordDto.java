package com.renqi.market.common;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 关键词实体
 * @author luhonggang
 * @date 2018/9/15
 * @since 1.0
 */
@Data
public class TaskWordDto {
    //@NotNull(message = "taskId不能为空")
    private Integer taskId;
    private Integer wordId;
    @NotBlank(message = "关键词不能为空")
    private String wordName;
    //@NotNull(message = "taskVisitor 总的访问数不能为空")
    private Integer taskVisitor;
    //@NotNull(message = "showNumber 展现数不能为空")
    private Integer showNumber;

    private Date createTime;

    private Date updateTime;


}
