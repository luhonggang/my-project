package com.renqi.market;

import com.renqi.market.dao.CustomerStateMapper;
import com.renqi.market.entity.CustomerState;
import com.renqi.market.util.JwtTokenUtil;
import com.renqi.market.util.redis.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.logging.Logger;

/**
 * @author luhonggang
 * @version 1.8.0
 * @date 2018/9/11 13:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.renqi.market.MarketApplication.class)
@SuppressWarnings("all")
public class CustomerTest {
    @Autowired
    CustomerStateMapper customerStateMapper;
    @Autowired
    RedisService redisService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testCache(){
        redisService.set("hhhh","1233");
        System.out.println("获取缓存 ： "+redisService.get("hhh"));
        stringRedisTemplate.opsForValue().set("aaa", "111");
        System.out.println(stringRedisTemplate.opsForValue().get("aaa"));
    }

    /**
     * token 生成测试
     */
    @Test
    public void testToken(){
        String customerId = "1";
        String issue = "renqi";
        String subjectIsShopId = "1";
        // 毫秒
        long expiredTime = 24*24*1*60*60*1000;
        // 生成token
        String token = JwtTokenUtil.createJWT(customerId,issue,
                subjectIsShopId,expiredTime,null);
        System.out.println("生成的token ： "+token);
    }

    @Test
    public void test(){
        // 用户进行注册的时候 初始化他的客户状态信息表 -----> 总的流程控制表
        CustomerState state = new CustomerState();
        // 只要任务不为 0 说明有任务
        state.setTotalTask(0);
        // 是否充值 1 是 0 否
        state.setIsRecharge(CustomerState.State.IS_RECHARGE_FALSE.getState());
        // 当前总的充值金额为 0
        state.setTotalMoney(0.00d);
        customerStateMapper.insert(state);
        //logger.info("+++++++++++ /market/customerRegister +++++++++++stateId:{}"+state.getCustomerStateId());
    }
}
