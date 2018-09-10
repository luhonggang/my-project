package com.renqi.market.dao;

import com.renqi.market.entity.TaskGood;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskGoodMapper {
    int deleteByPrimaryKey(Integer goodId);

    int insert(TaskGood record);

    int insertSelective(TaskGood record);

    TaskGood selectByPrimaryKey(Integer goodId);

    int updateByPrimaryKeySelective(TaskGood record);

    int updateByPrimaryKey(TaskGood record);
}