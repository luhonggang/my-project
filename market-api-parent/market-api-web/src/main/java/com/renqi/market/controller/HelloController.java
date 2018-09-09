package com.renqi.market.controller;

import com.alibaba.fastjson.JSONObject;
import com.renqi.market.service.CustomerService;
import com.renqi.market.util.JwtTokenUtil;
import com.renqi.market.util.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController {
    private final static Logger logger = LoggerFactory.getLogger(HelloController.class);
    @Autowired
    private CustomerService customerService;

    @Autowired
    private RedisService redisService;

    public static void main(String[] args) {

        logger.info("token ： "+JwtTokenUtil.getToken("18589072284"));
    }
    /**
     * redis测试
     */
    @RequestMapping("/redis")
    public String addRedisMsg() {
        if(redisService.get("redis") == null){
            logger.info("++++++++++redis 中不存在当前key ++++++++++");
        }
        redisService.set("redistest","11111111111111111111111");
        return "redis_add_is_success";
    }

    /**
     *
     */
    private String url = "http://testhhh.51huihuahua.com";
    @RequestMapping("/selectCustomer")
    public JSONObject hello(HttpServletRequest request) {
        try {
            return customerService.selectCustomerList("11");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }






}
