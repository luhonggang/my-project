package com.renqi.market.dao;

import com.renqi.market.entity.TaskSpecial;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskSpecialMapper {
    int deleteByPrimaryKey(Integer taskSpecialId);

    int insert(TaskSpecial record);

    int insertSelective(TaskSpecial record);

    TaskSpecial selectByPrimaryKey(Integer taskSpecialId);

    int updateByPrimaryKeySelective(TaskSpecial record);

    int updateByPrimaryKey(TaskSpecial record);
}