//package com.renqi.market.util.redis;
//
//import com.honglu.utils.PropertiesUtil;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
//@Configuration
//public class RedisCommonConfig {
//
//    private final static int maxIdle = Integer.parseInt(PropertiesUtil.get("spring.redis.pool.max-idle"));
//    private final static String host = PropertiesUtil.get("spring.redis.host");
//    private final static int port = Integer.parseInt(PropertiesUtil.get("spring.redis.port"));
//    private final static int timeout = Integer.parseInt(PropertiesUtil.get("spring.redis.timeout"));
//    private final static String password = PropertiesUtil.get("spring.redis.password");
//
//    @Bean
//    public JedisPoolConfig getRedisConfig() {
//        JedisPoolConfig config = new JedisPoolConfig();
//        return config;
//    }
//
//    @Bean
//    public JedisPool getJedisPool() {
//        JedisPoolConfig config = new JedisPoolConfig();
//        // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
//        config.setMaxIdle(maxIdle);
//        // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
//        config.setTestOnBorrow(true);
//        JedisPool pool = new JedisPool(config, host, port, timeout, password);
//        return pool;
//    }
//}