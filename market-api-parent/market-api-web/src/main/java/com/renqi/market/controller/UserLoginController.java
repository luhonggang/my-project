package com.renqi.market.controller;

import com.renqi.market.common.BaseCustomer;
import com.renqi.market.common.BaseResultMsg;
import com.renqi.market.common.CustomerRegister;
import com.renqi.market.exception.GlobalExceptionHandler;
import com.renqi.market.service.CustomerService;
import com.renqi.market.util.*;
import com.renqi.market.util.redis.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${token.expiredTime}")
    private Long expiredTime;
    /**
     * 用户登录接口
     * @param
     * @return         用户信息
     */
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public BaseResultMsg customerLogin(@RequestBody CustomerRegister customer){
//        @RequestParam("phone") String phone,
//        @RequestParam("passWord") String passWord
//        BaseResultMsg baseResultMsg = new BaseResultMsg();
        String phone = customer.getMobile();
        String passWord = customer.getPassWord();
//        if(StringUtils.isNotBlank(phone)){ todo 前端校验手机号是否为空
        BaseResultMsg baseResultMsg = new BaseResultMsg();
         // 校验手机号是否正确
         if(StringHandleUtils.checkCellphone(phone)){
           // 检查输入的密码是否正确
//             String pwdMd5Str = MD5Utils.md5(passWord);
//            if(StringHandleUtils.checkIsEncode(pwdMd5Str)){
                // 输入的手机号码和密码都符合查库查询用户信息
                BaseCustomer lognCustomer = customerService.selectCustomerByPhonePwd(phone,passWord);
                if(null != lognCustomer){
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
                }
                // 说明用户没有注册 先注册
                return ResultMsgUtil.setResponse(baseResultMsg,SystemCode.SYSTEM_LOGIN_ERROR.getCode(), SystemCode.SYSTEM_LOGIN_ERROR.getMsg(),lognCustomer);
//            }else{
//                return  ResultMsgUtil.setResponse(baseResultMsg,SystemCode.PASS_WORD_ERROR.getCode(), SystemCode.PASS_WORD_ERROR.getMsg(),null);
//            }
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
        BaseResultMsg base = new BaseResultMsg(SystemCode.SYSTEM_SUCCESS.getCode(),SystemCode.SYSTEM_SUCCESS.getMsg());
        try{
            if(customerService.checkMobileIsRegister(user.getMobile())){
                return ResultMsgUtil.setCodeMsg(base,SystemCode.MOBILE_IS_REGISTER.getCode(),"您已注册,请登录");
            }
            Map<String,String> map  = new HashMap<>();
            map.put("type","register");
            map.put("mobileCode",user.getMobileCode());
            // 前端校验手机号码 为非空
            if(customerService.checkMobileCodeIsOk(user.getMobile(),map)){
               customerService.registerCustomer(user);
            }else{
               return ResultMsgUtil.setCodeMsg(base,SystemCode.INPUT_CODE_ERROR.getCode(),SystemCode.INPUT_CODE_ERROR.getMsg());
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
     * @return
     */
    @RequestMapping(value = "/mobileCode", method = RequestMethod.GET)
    public BaseResultMsg getMobileCode(String mobile) {
        BaseResultMsg base = new BaseResultMsg(SystemCode.SYSTEM_SUCCESS.getCode(),SystemCode.GET_MOBILE_CODE.getMsg());
        /**
         *  验证手机号码是否注册过,注册过的不让注册
         */
        if(StringUtils.isEmpty(mobile) ){
            return ResultMsgUtil.setCodeMsg(base,SystemCode.MOBILE_IS_ERROR.getCode(),SystemCode.MOBILE_IS_ERROR.getMsg());
        }else{
            // 注册过的不再发送验证码
            if(customerService.checkMobileIsRegister(mobile)){
                // 说明手机号未注册
                String type = "register";
                String template = new String("您的验证码是：ABCD。请不要把验证码泄露给其他人。");
                Map<String,String> map = new HashMap<>();
                map.put("type",type);
                map.put("template",template);
                return  customerService.getIdentiFyingCode(mobile,map);
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
