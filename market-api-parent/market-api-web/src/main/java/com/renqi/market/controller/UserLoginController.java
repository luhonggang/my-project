package com.renqi.market.controller;

import com.renqi.market.common.BaseCustomer;
import com.renqi.market.common.BaseResultMsg;
import com.renqi.market.common.CustomerRegister;
import com.renqi.market.exception.GlobalExceptionHandler;
import com.renqi.market.service.CustomerService;
import com.renqi.market.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author luhonggang
 * @date 2018/9/8
 * @since 1.0
 */
@RestController
@RequestMapping("/customer")
@SuppressWarnings("all")
public class UserLoginController extends GlobalExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RedisService redisService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // token 失效时间 60s
    @Value("${login.token.expiredTime}")
    private Long expiredTime;
    /**
     * 用户登录接口
     * @param
     * @return         用户信息
     */
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public BaseResultMsg customerLogin(@RequestBody CustomerRegister customer){
        String phone = customer.getPhone();
        String passWord = customer.getPassWord();
       // todo 前端校验手机号是否为空
        BaseResultMsg baseResultMsg = new BaseResultMsg();
         // 校验手机号是否正确
         if(StringHandleUtils.checkCellphone(phone)){
            // 输入的手机号码和密码都符合查库查询用户信息
            BaseCustomer lognCustomer = customerService.selectCustomerByPhonePwd(phone,null);
            if(null != lognCustomer){
                // 比较密码是否正确
                if(!passWord.equalsIgnoreCase(lognCustomer.getPassWord())){
                    return ResultMsgUtil.setResponse(baseResultMsg,SystemCode.PASS_WORD_INCORRECT.getCode(), SystemCode.PASS_WORD_INCORRECT.getMsg(),lognCustomer);
                }
                // 重新生成token
                String token = JwtTokenUtil.createJWT(lognCustomer.getCustomerId()+"","renqi",
                        lognCustomer.getShopId()+"",expiredTime,null);
                //redisService.set("user_token:"+customerPhone,token);
                lognCustomer.setToken(token);
                // 过期时间
                lognCustomer.setExpireTime((System.currentTimeMillis()+expiredTime)+"");
                // 此时要将token更新入库
                lognCustomer.setLoginTime(new Date());
                lognCustomer.setUpdateTime(new Date());
                customerService.updateCustomerToken(lognCustomer);
                return ResultMsgUtil.setResponse(baseResultMsg,SystemCode.SYSTEM_SUCCESS_LOGIN.getCode(), SystemCode.SYSTEM_SUCCESS_LOGIN.getMsg(),lognCustomer);
            }else{
             // 说明用户没有注册 先注册
             return ResultMsgUtil.setResponse(baseResultMsg,SystemCode.SYSTEM_LOGIN_ERROR.getCode(), SystemCode.SYSTEM_LOGIN_ERROR.getMsg(),lognCustomer);
            }
           }else{
                return  ResultMsgUtil.setResponse(baseResultMsg,SystemCode.PHONE_NUMBER_ERROR.getCode(), SystemCode.PHONE_NUMBER_ERROR.getMsg(),null);
          }
    }

    /**
     * 注册用户
     * @param user 用户信息
     * @return     BaseResultMsg
     */
    @RequestMapping(value = "/customerRegister", method = RequestMethod.POST)
    public BaseResultMsg customerRegister(@RequestBody CustomerRegister user) {
        BaseResultMsg base = new BaseResultMsg(SystemCode.SYSTEM_SUCCESS.getCode(),"注册成功");
        try{
            if(customerService.checkMobileIsRegister(user.getPhone())){
                return ResultMsgUtil.setCodeMsg(base,SystemCode.SYSTEM_SUCCESS.getCode(),"您已注册,请登录");
            }
            // 若存在说明验证码有效的，否则无效
            if(stringRedisTemplate.hasKey("register_"+user.getPhone()+"_"+user.getMobileCode())){
                base = customerService.registerCustomer(user);
            }else{
                if(!stringRedisTemplate.hasKey("register_"+user.getPhone()+"_HAVE_CODE")){
                    return ResultMsgUtil.setCodeMsg(base,SystemCode.INPUT_CODE_ERROR.getCode(),SystemCode.INPUT_CODE_ERROR.getMsg());
                }
               return ResultMsgUtil.setCodeMsg(base,SystemCode.MOBILE_EXPIRED.getCode(),SystemCode.MOBILE_EXPIRED.getMsg());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultMsgUtil.setCodeMsg(base,SystemCode.SYSTEM_ERROR.getCode(),SystemCode.SYSTEM_ERROR.getMsg());
        }
        return base;
    }

    /**
     * 依据手机号码获取验证码
     * @param mobile 手机号码
     * @param mobileCode 验证码
     * @return
     */
    @RequestMapping(value = "/mobileCode", method = RequestMethod.POST)
    public BaseResultMsg getMobileCode(@RequestBody CustomerRegister customer) {
        BaseResultMsg base = new BaseResultMsg(SystemCode.SYSTEM_SUCCESS.getCode(),SystemCode.GET_MOBILE_CODE.getMsg());
        /**
         *  验证手机号码是否注册过,注册过的不让注册
         */
        String phone = customer.getPhone();
        String mobileCode = customer.getMobileCode();
        if(StringUtils.isEmpty(phone)){
            return ResultMsgUtil.setCodeMsg(base,SystemCode.MOBILE_IS_ERROR.getCode(),SystemCode.MOBILE_IS_ERROR.getMsg());
        }else{
            String type = "register";
            if(StringUtils.isNotBlank(mobileCode)){
              // 1. 说明是校验验证码是否正确
                if(stringRedisTemplate.hasKey(type+"_"+phone+"_"+mobileCode)){
                  // 存在该值说明输入的验证码是正确的 否则是输入有误
                    return ResultMsgUtil.setCodeMsg(base,SystemCode.SYSTEM_SUCCESS.getCode(),SystemCode.SYSTEM_SUCCESS.getMsg());
                }
                    return ResultMsgUtil.setCodeMsg(base,SystemCode.INPUT_CODE_ERROR.getCode(),SystemCode.INPUT_CODE_ERROR.getMsg());
            }
            // 2.说明是获取验证码 注册过的不再发送验证码
            boolean flag = customerService.checkMobileIsRegister(phone);
            if(!flag){
                // 3.说明手机号未注册
                if(stringRedisTemplate.hasKey(type+"_"+phone)){
                    return ResultMsgUtil.setCodeMsg(base,SystemCode.MOBILE_IS_EXIST.getCode(),SystemCode.MOBILE_IS_EXIST.getMsg());
                }
                Map<String,String> map = new HashMap<>();
                map.put("type",type);
                map.put("template","SMS_77560073");
                return  customerService.getIdentiFyingCode(phone,map);
            }else{
                return ResultMsgUtil.setCodeMsg(base,SystemCode.MOBILE_IS_REGISTER.getCode(),SystemCode.MOBILE_IS_REGISTER.getMsg());
            }
        }
    }

    /**
     * 用户获取验证码以及验证当前手机号码接受的验证码是否正确
     * @param phone 手机号码
     * @param code  验证码
     * @return
     */
//    @RequestMapping(value="/register",method = RequestMethod.GET)
//    public BaseResultMsg customerCode(String phone,String code){
//        BaseResultMsg resultMsg = new BaseResultMsg();
//        if(StringUtils.isNotBlank(code) && StringUtils.isNotBlank(phone)){
//           // 手机号码和验证码都不为空的时候 校验验证码是否正确
//        }else {
//            // 发送验证码
//
//        }
//        return resultMsg;
//    }


}
