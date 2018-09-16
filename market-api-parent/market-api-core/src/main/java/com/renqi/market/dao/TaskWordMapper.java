package com.renqi.market.dao;

import com.renqi.market.common.TaskWordDto;
import com.renqi.market.entity.TaskWord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskWordMapper {

    int insert(TaskWord record);

    int updateByPrimaryKeySelective(TaskWord record);

    int updateByPrimaryKey(TaskWord record);

    void insertTaskWord(TaskWordDto dto);
}