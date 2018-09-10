package com.renqi.market.dao;

import com.renqi.market.entity.CustomerTask;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerTaskMapper {
    int deleteByPrimaryKey(Integer taskId);

    int insert(CustomerTask record);

    int insertSelective(CustomerTask record);

    CustomerTask selectByPrimaryKey(Integer taskId);

    int updateByPrimaryKeySelective(CustomerTask record);

    int updateByPrimaryKey(CustomerTask record);
}