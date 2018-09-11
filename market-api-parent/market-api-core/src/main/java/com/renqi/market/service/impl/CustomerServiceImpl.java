package com.renqi.market.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.renqi.market.common.BaseCustomer;
import com.renqi.market.common.BaseResultMsg;
import com.renqi.market.common.CustomerRegister;
import com.renqi.market.dao.CustomerStateMapper;
import com.renqi.market.entity.Customer;
import com.renqi.market.dao.CustomerMapper;
import com.renqi.market.entity.CustomerState;
import com.renqi.market.service.CustomerService;
import com.renqi.market.util.MobileCodeUtil;
import com.renqi.market.util.ResultMsgUtil;
import com.renqi.market.util.SystemCode;
import com.renqi.market.util.redis.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.*;

@Service
@SuppressWarnings("all")
public class CustomerServiceImpl implements CustomerService {

	private final static Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private CustomerStateMapper customerStateMapper;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private RedisService redisService;


//	Optional<Customer> oldcustomer = Optional.ofNullable();
//	if (oldcustomer.isPresent()){}

	/**
	 * 去哪接平台联合登陆业务
	 * 是否为机构的新注册用户; 1:是 , 2：通过渠道推送的老用户，3：其他渠道推送的老用户
	 * @param phoneNum   	手机号(可能MD5加密)
	 * @param maskType  	0 明文, 1 密文
	 * @return              BaseResultMsg
	 */
	@Override
	@Transactional(isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
	public JSONObject selectCustomerList(String phoneNum) throws Exception {
		final List<Customer> customerList = new ArrayList<Customer>();
		Map<String, Object> resultMap = new HashMap<>();
		String key = "REGISTER_LOCK_1_" + phoneNum;
		if (redisService.exists(key)) {
			resultMap.put("phoneNo", phoneNum);
			throw new Exception(" 重复申请 " + phoneNum + " ");
		}
		try {
			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					logger.info("开始 ++++++++++ 注册");
//					customerList = customerMapper.selectCustomerList();
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("taskExecutor.execute task reject {}");
		}
		JSONObject obj = new JSONObject();
		obj.put("data",customerMapper.selectCustomerList());
	return obj;
	}

	@Override
	public void updateCustomerToken(BaseCustomer lognCustomer) {
		customerMapper.updateCustomerToken(lognCustomer);
	}

	/**
	 *
	 * @param mobile
	 * @param map
	 * @return
	 */
	@Override
	public boolean checkMobileCodeIsOk(String mobile,Map<String,String> map) {
		boolean flag = true;
		try {
		    String ywType = map.get("type");
			if (redisService.exists(ywType+"_"+mobile+"_"+map.get("mobileCode"))) {
               // 比较用户输入的验证码和 是否是发送的验证码
				if(!map.get("mobileCode").equals(redisService.get(ywType+"_"+mobile+"_"+map.get("mobileCode")))) {
					flag = false;
				}
			} else {
				flag =  false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("系统异常,请联系相关人员处理");
		}
		return  flag;
	}

	/**
	 * 检查当前手机号码是否注册过
	 * @param mobile 手机号码
	 * @return true  没注册过 否则已注册
	 */
	@Override
	public boolean checkMobileIsRegister(String mobile) {
		return  customerMapper.checkMobileIsRegister(mobile) != 0 ? true : false;
	}

	/**
	 * 获取验证码 并存入到Redis
	 * @param mobile
	 * @param map
	 * @return BaseResultMsg
	 */
	@Override
	public BaseResultMsg getIdentiFyingCode(String mobile,  Map<String,String> map) {
		BaseResultMsg base = new BaseResultMsg("200", "成功获取验证码信息");
		try {
				base = getMobileCodeAndMsg(mobile,map);
		}catch (Exception e ){
			logger.error("++++++++++ 验证码已经生成过,2分钟内不可重复生成 ++++++++++");
            return ResultMsgUtil.setCodeMsg(base,SystemCode.SYSTEM_ERROR.getCode(),SystemCode.SYSTEM_ERROR.getMsg());
		}
		return base;
	}

    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public void registerCustomer(CustomerRegister user) throws Exception{
    	try{
			// 用户进行注册的时候 初始化他的客户状态信息表 -----> 总的流程控制表
			CustomerState state = new CustomerState();
			// 只要任务不为 0 说明有任务
			state.setTotalTask(0);
			// 是否充值 1 是 0 否
			state.setIsRecharge(CustomerState.State.IS_RECHARGE_FALSE.getState());
			// 当前总的充值金额为 0
			state.setTotalMoney(0.00d);
			customerStateMapper.insert(state);
			logger.info("+++++++++++ /market/customerRegister +++++++++++stateId:{}",state.getCustomerStateId());

			Customer customer = new Customer();
			customer.setCustomerStateId(state.getCustomerStateId());
			customer.setPhone(user.getPhone());
			customer.setPassWord(user.getPassWord());
			customer.setLevelId(1);
			customer.setShopId(null);
			customer.setCreateTime(new Date());
			customerMapper.insert(customer);
		}catch (Exception e){
			logger.error(e.getMessage(),"+++++++++++ /market/customerRegister +++++++++++ error");
			e.printStackTrace();
		}
    }

    /**
	 * 依据手机号码和 以及指定key的业务类型,模板 来生成验证码 并存在Redis中
	 * @param mobile
	 * @param typeAndTemplate
	 * @return
	 */
	public  BaseResultMsg getMobileCodeAndMsg(String mobile,Map<String,String> typeAndTemplate){
            BaseResultMsg base = new BaseResultMsg("200", "成功获取验证码信息");
            Map<String,String> map = MobileCodeUtil.getMobileCode(mobile,typeAndTemplate.get("template"));
			String ywType = typeAndTemplate.get("type");
			if(StringUtils.isNotBlank(ywType)){
				if ("2".equals(map.get("msg"))){
					redisService.set(ywType+"_"+mobile,120L);
					// 记录验证码并设置失效时间
					redisService.set(ywType+"_"+mobile+"_"+map.get("mobileCode"),300L);
					// 记录当前注册的手机号是点击了发送验证码按钮
					redisService.set(ywType+"_"+mobile+"_HAVE_CODE",300L);
					base.setData(map.get("mobileCode"));
					return base;
				}else {
                    return ResultMsgUtil.setCodeMsg(base,SystemCode.MOBILE_SEND_ERROR.getCode(),SystemCode.MOBILE_SEND_ERROR.getMsg());
				}
			}else {
                logger.info("+++++++++++ 未指定业务类型 ++++++++++");
                return ResultMsgUtil.setCodeMsg(base, SystemCode.SYSTEM_ERROR.getCode(), SystemCode.SYSTEM_ERROR.getMsg());
            }
	}

	/**
	 * 用户登录返回用户信息
	 * @param phone
	 * @param passWord
	 * @param <T>
	 * @return
	 */
	@Override
	public BaseCustomer selectCustomerByPhonePwd(String phone,String passWord) {
		return customerMapper.selectCustomerByPhonePwd(phone,passWord);
	}
}
