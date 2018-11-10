package com.renqi.market.controller;

import com.renqi.market.common.BaseResult;
import com.renqi.market.common.BaseResultMsg;
import com.renqi.market.common.CustomerParamDto;
import com.renqi.market.common.CustomerTaskDto;
import com.renqi.market.service.CustomerTaskService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 任务发布及统计任务总量业务实现
 * @author luhonggang
 * @date 2018/9/15
 * @since 1.0
 */
@RestController
@RequestMapping("task")
@Validated
public class CustomerTaskController {
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(CustomerTaskController.class);

    @Autowired
    private CustomerTaskService customerTaskService;

    /**
     * 用户登录后查询用户信息
     * @param customerId
     * @return
     */
    @RequestMapping(value = "/queryCustomerMsg.json",method = RequestMethod.GET)
    public BaseResultMsg countCustomerTask(String customerId){
        return customerTaskService.countCustomerTask(customerId);
    }

    /**
     * 任务批量发布保存业务
     * @param task  任务数
     * @return
     */
    @RequestMapping(value = "/saveCustomerTask.json",method = RequestMethod.POST)
    public BaseResult saveCustomerTask(@RequestBody @Valid CustomerTaskDto task){
        return customerTaskService.saveCustomerTask(task);
    }

    /**
     * 任务查询
     * @param  dto 任务数
     * @return
     */
    @RequestMapping(value = "/queryCustomerTask.json",method = RequestMethod.GET)
    public BaseResultMsg queryCustomerTask(@RequestBody CustomerParamDto dto){
        return customerTaskService.queryCustomerTask(dto);
    }

}
