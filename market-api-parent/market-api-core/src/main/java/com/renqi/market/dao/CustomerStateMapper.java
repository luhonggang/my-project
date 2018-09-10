package com.renqi.market.dao;

import com.renqi.market.entity.CustomerState;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerStateMapper {
    int deleteByPrimaryKey(Integer customerStateId);

    int insert(CustomerState record);

    int insertSelective(CustomerState record);

    CustomerState selectByPrimaryKey(Integer customerStateId);

    int updateByPrimaryKeySelective(CustomerState record);

    int updateByPrimaryKey(CustomerState record);
}