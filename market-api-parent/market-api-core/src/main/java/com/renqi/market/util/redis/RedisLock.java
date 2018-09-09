package com.renqi.market.util.redis;

public interface RedisLock extends  AutoCloseable {
    /**
     * 释放分布式锁
     */
    void unlock();

}
