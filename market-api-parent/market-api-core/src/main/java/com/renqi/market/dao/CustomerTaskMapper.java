package com.renqi.market.dao;

import com.renqi.market.common.CustomerParamDto;
import com.renqi.market.common.CustomerTaskDto;
import com.renqi.market.common.CustomerTaskVo;
import com.renqi.market.entity.CustomerTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomerTaskMapper {
    int deleteByPrimaryKey(Integer taskId);

    int insert(CustomerTask record);

    int updateByPrimaryKeySelective(CustomerTask record);

    int updateByPrimaryKey(CustomerTask record);

    int insertCustomerTask(CustomerTaskDto task);

    List<CustomerTaskVo> queryCustomerTask(CustomerParamDto dto);
}