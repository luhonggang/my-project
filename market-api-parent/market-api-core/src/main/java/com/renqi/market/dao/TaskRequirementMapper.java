package com.renqi.market.dao;

import com.renqi.market.entity.TaskRequirement;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskRequirementMapper {
    int deleteByPrimaryKey(Integer requireRelId);

    int insert(TaskRequirement record);

    int insertSelective(TaskRequirement record);

    TaskRequirement selectByPrimaryKey(Integer requireRelId);

    int updateByPrimaryKeySelective(TaskRequirement record);

    int updateByPrimaryKey(TaskRequirement record);
}