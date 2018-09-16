package com.renqi.market.dao;

import com.renqi.market.common.TaskTemplateVo;
import com.renqi.market.entity.TaskTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskTemplateMapper {
    int insert(TaskTemplate record);

    int insertSelective(TaskTemplate record);

    int updateByPrimaryKey(TaskTemplate record);

    List<TaskTemplateVo> queryTemplate(@Param("taskType") String taskType, @Param("arrayStr") String arrayStr);
}