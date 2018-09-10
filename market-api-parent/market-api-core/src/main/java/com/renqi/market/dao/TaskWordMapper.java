package com.renqi.market.dao;

import com.renqi.market.entity.TaskWord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskWordMapper {
    int deleteByPrimaryKey(Integer wordId);

    int insert(TaskWord record);

    int insertSelective(TaskWord record);

    TaskWord selectByPrimaryKey(Integer wordId);

    int updateByPrimaryKeySelective(TaskWord record);

    int updateByPrimaryKey(TaskWord record);
}