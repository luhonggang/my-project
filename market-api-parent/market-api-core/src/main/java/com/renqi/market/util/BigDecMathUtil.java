package com.renqi.market.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 金额计算工具类
 */
public class BigDecMathUtil {
    private static final int DEF_DIV_SCALE = 10; //这个类不能实例化

    /**
     * 提供精确的加法运算
     * @param d1
     * @param d2
     * @return
     */
    public static double add(double d1,double d2){
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算
     * @param d1
     * @param d2
     * @return
     */
    public static double subtract(double d1,double d2){
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     * @param d1
     * @param d2
     * @return
     */
    public static double multiply(double d1,double d2){
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到小数点以后10位，以后的数字四舍五入。
     * @param d1
     * @param d2
     * @return
     */
    public static double divide(double d1,double d2){
        return divide(d1,d2,DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double divide(double d1,double d2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        if (d2 == 0.0) {
            throw new IllegalArgumentException(
                    "The two param cann't be zero");
        }
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 浮点数相除 返回BigDecimal 类型的参数
     * param d1
     * param d2
     * param scale
     * return
     */
    public static  BigDecimal divideForDecimal(double d1,double d2,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        if (d2 == 0.0) {
            throw new IllegalArgumentException(
                    "The two param cann't be zero");
        }
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * double小数转百分数
     * @param d
     * @return
     */
    public static String doubleToPercent(Double d) {
        return new DecimalFormat("0.00%").format(d);
    }

    /**
     * 字符串小数转百分数
     * @param s
     * @return
     */
    public static BigDecimal doubleToPercent(String s) {
        return new BigDecimal(new DecimalFormat("0.00%").format(s));
    }
}
