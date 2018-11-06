package com.bootdo.common.dao;

import com.bootdo.common.domain.CustomerInfoDto;
import com.bootdo.common.domain.FileDO;
import com.bootdo.common.utils.Query;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerMapper {
    /**
     * 查询用户的账户余额 会员级别 折扣等信息
     * @return
     */
    List<CustomerInfoDto> queryListCustomerMsg(Query query);

    int count(Query query);

    CustomerInfoDto queryCustomerById(Long customerId);

    int updateLevelById(CustomerInfoDto customerInfoDto);

    int updateTotalMoney(CustomerInfoDto customerInfoDto);

    CustomerInfoDto selectStateId(@Param("customerId") Integer customerId);

    List<CustomerInfoDto> queryListPhone();

    CustomerInfoDto selectCurrentMoney(CustomerInfoDto customerInfoDto);
}