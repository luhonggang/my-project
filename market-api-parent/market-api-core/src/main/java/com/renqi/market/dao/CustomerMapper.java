package com.renqi.market.dao;

import com.renqi.market.common.BaseCustomer;
import com.renqi.market.entity.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerMapper {
    List<Customer> selectCustomerList();
    int deleteByPrimaryKey(Integer customerId);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer customerId);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    /**
     * 依据手机号和密码查询出用户信息
     * @param phone     手机号码
     * @param passWord  密码
     * @return          Customer
     */
    BaseCustomer selectCustomerByPhonePwd(@Param("phone") String phone, @Param("passWord") String passWord);

    void updateCustomerToken(BaseCustomer lognCustomer);

    Integer checkMobileIsRegister(@Param("phone") String phone);
}