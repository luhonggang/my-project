package com.renqi.market.dao;

import com.renqi.market.entity.TaskTemplate;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskTemplateMapper {
    int deleteByPrimaryKey(Integer templateId);

    int insert(TaskTemplate record);

    int insertSelective(TaskTemplate record);

    TaskTemplate selectByPrimaryKey(Integer templateId);

    int updateByPrimaryKeySelective(TaskTemplate record);

    int updateByPrimaryKey(TaskTemplate record);
}