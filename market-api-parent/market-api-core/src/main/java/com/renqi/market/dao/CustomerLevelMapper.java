package com.renqi.market.dao;

import com.renqi.market.entity.CustomerLevel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerLevelMapper {
    int deleteByPrimaryKey(Integer levelId);

    int insert(CustomerLevel record);

    int insertSelective(CustomerLevel record);

    CustomerLevel selectByPrimaryKey(Integer levelId);

    int updateByPrimaryKeySelective(CustomerLevel record);

    int updateByPrimaryKey(CustomerLevel record);
}