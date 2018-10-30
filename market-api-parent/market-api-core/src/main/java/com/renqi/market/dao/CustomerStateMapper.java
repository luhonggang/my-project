package com.renqi.market.dao;

import com.renqi.market.entity.CustomerState;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CustomerStateMapper {
    int deleteByPrimaryKey(Integer customerStateId);

    int insert(CustomerState record);

    int insertSelective(CustomerState record);

    CustomerState selectByPrimaryKey(Integer customerStateId);

    int updateByPrimaryKeySelective(CustomerState record);

    void updateByCustomerStateId(@Param("customerStateId") String customerStateId, @Param("money") String money);
}