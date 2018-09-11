package com.renqi.market.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @author luhonggang
 * @version 1.8.0
 * @date 2018/9/11 10:06
 */
@Component
public class Task {
    /**
     * private final static Logger logger = LoggerFactory.getLogger(Task.class);
     */
    private  Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    /**
     *  每天23点执行任务 0/1 * * * * *
     *  0 0 23 * * ?
     */
    //@Scheduled(cron = "0 0 23 * * ?")
    public void taskWork(){
        logger.info("++++++++++++++++++ update mk_market_percent table task begin ++++++++++++++++++");
        try{
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    logger.info("++++++++++++++++++++ 开始执行更新操作 ++++++++++++++++++++");
                }
            });

        }catch(Exception e){
            e.printStackTrace();
            logger.error("taskExecutor.execute task reject {}");
            logger.info("++++++++++++++++++++ 执行更新操作异常 ++++++++++++++++++++");
        }
    }
}
