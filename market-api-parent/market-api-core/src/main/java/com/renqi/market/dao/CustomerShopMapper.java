package com.renqi.market.dao;

import com.renqi.market.entity.CustomerShop;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerShopMapper {
    int deleteByPrimaryKey(Integer shopId);

    int insert(CustomerShop record);

    int insertSelective(CustomerShop record);

    CustomerShop selectByPrimaryKey(Integer shopId);

    int updateByPrimaryKeySelective(CustomerShop record);

    int updateByPrimaryKey(CustomerShop record);
}