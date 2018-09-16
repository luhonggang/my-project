package com.renqi.market.service;

import com.renqi.market.common.BaseResult;
import com.renqi.market.common.BaseResultMsg;
import com.renqi.market.common.CustomerParamDto;
import com.renqi.market.common.CustomerTaskDto;

/**
 * @author luhonggang
 * @date 2018/9/15
 * @since 1.0
 */
public interface CustomerTaskService {
    BaseResultMsg countCustomerTask(String customerId);

    BaseResult saveCustomerTask(CustomerTaskDto task);

    BaseResultMsg queryCustomerTask(CustomerParamDto dto);
}
