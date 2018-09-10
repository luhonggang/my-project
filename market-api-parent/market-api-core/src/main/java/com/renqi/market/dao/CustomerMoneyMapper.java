package com.renqi.market.dao;

import com.renqi.market.entity.CustomerMoney;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMoneyMapper {
    int deleteByPrimaryKey(Integer amountId);

    int insert(CustomerMoney record);

    int insertSelective(CustomerMoney record);

    CustomerMoney selectByPrimaryKey(Integer amountId);

    int updateByPrimaryKeySelective(CustomerMoney record);

    int updateByPrimaryKey(CustomerMoney record);
}