package com.renqi.market.dao;

import com.renqi.market.entity.CustomerState;

public interface CustomerStateMapper {
    int deleteByPrimaryKey(Integer customerStateId);

    int insert(CustomerState record);

    int insertSelective(CustomerState record);

    CustomerState selectByPrimaryKey(Integer customerStateId);

    int updateByPrimaryKeySelective(CustomerState record);

    int updateByPrimaryKey(CustomerState record);
}