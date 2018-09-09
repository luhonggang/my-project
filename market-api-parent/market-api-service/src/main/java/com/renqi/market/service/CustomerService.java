package com.renqi.market.service;

import com.alibaba.fastjson.JSONObject;
import com.renqi.market.common.BaseCustomer;
import com.renqi.market.common.BaseResultMsg;
import com.renqi.market.common.CustomerRegister;

import java.util.Map;

public interface CustomerService {

	JSONObject selectCustomerList(String phoneNum) throws Exception;

    <T> T selectCustomerByPhonePwd(String phone,String passWord);

    void updateCustomerToken(BaseCustomer lognCustomer);

    boolean checkMobileCodeIsOk(String mobile, Map<String, String> map);

    boolean checkMobileIsRegister(String mobile);

    BaseResultMsg getIdentiFyingCode(String mobile, Map<String, String> map);

    void registerCustomer(CustomerRegister user);
}
