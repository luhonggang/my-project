package com.renqi.market.util;

import java.time.LocalTime;

/**
 * @author luhonggang
 * @version 1.8.0
 * @date 2018/9/11 16:40
 */
public class Constants {
    /**数据字段前缀 */
    public static final String DICT_KEY = "MARKET:SYSTEM:DICT:MARKET_THRESHOLD_VALUE";
    /**
     * 市场渠道注册量计算扣量的阈值 MARKET_REGISTER_NUMBER
     */
    public static final String MARKET_REGISTER_NUMBER="MARKET_REGISTER_NUMBER";

    public static void main(String[] args) {
        String a =  "3";
        System.out.println("输出的结果"+"3".compareTo("8"));
        System.out.println("输出的结果"+"2018-09-05".compareTo("2018-08-21"));

        LocalTime hour = LocalTime.now().withNano(0).withMinute(0);
        System.out.println("当前几点 ： "+hour);
        System.out.println("2018-08-12".equals("2018-08-12"));
    }
}
