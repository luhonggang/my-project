package com.renqi.market.util;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 * @author luhonggang
 * @version v1.0
 * @date 2018/7/25 17:31
 */
public class StringHandleUtils {
    private static final String prefixHttp = "http://";
    private static final String prefixHttps = "https://";
    private static final String prefix3W = "www.";
    private static final String regixPhone = "";
    /**
     * 正则表达式 ：验证有区号的手机号
     */
    public static final String REGEX_NUMBER = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,1,2,5-9])|(177))\\\\d{8}$";

    /**
     * 正则表达式 ：验证没有区号的手机号码
     */
    private static final String NO_AREA_REGEX_NUMBER = "^[1-9]{1}[0-9]{5,8}$";

    /**
     * 返回给定的字符的匹配的字串的长度
     *
     * @param prefixDomainUrl 域名前缀
     * @param str             字符
     * @return int
     */
    public static String getDomainAddress(String prefixDomainUrl, String str) {
        String currentStr = "";
        if (StringUtils.isNotEmpty(prefixDomainUrl) && StringUtils.isNotEmpty(str)) {
            if (str.startsWith(prefixHttp) || str.startsWith(prefixHttps)) {
                int strLenth = str.length();
                if (strLenth == 7) {
                    currentStr = str.substring(prefixHttp.length());
                } else {
                    currentStr = str.substring(prefixHttps.length());
                }
            } else {
                // 以www开始
                if (str.startsWith(prefix3W)) {
                    currentStr = str;
                }
            }
            if (currentStr.contains("/")) {
                return prefixDomainUrl.concat(currentStr.substring(currentStr.indexOf("/")));
            }
        }
        return str;
    }

    /**
     * 给定的字符是否在指定的字符数组内
     *
     * @param desStr   判定字符
     * @param strArray 字符数组
     * @return boolean
     */
    public static boolean containsStr(String desStr, String[] strArray) {
        if (strArray != null && strArray.length != 0) {
            for (int i = 0; i < strArray.length; i++) {
                if (strArray[i].equalsIgnoreCase(desStr)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 名值对之间拼接指定的字符组成指定格式拼接字符串
     *
     * @param paramMap 有序的键值对Map
     * @return String
     */
    public static String paramTreeMapToString(TreeMap<String, String> paramMap) {
        StringBuilder paramStrBuilder = new StringBuilder();

        Iterator<Map.Entry<String, String>> it = paramMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entrySet = it.next();
            paramStrBuilder.append(entrySet.getKey()).append('=').append(entrySet.getValue()).append('&');
        }
        return paramStrBuilder.substring(0, paramStrBuilder.length() - 1);
    }

    /**
     *
     * 方法用途: 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串<br>
     * 实现步骤: <br>
     * @param paraMap   要排序的Map对象
     * @return
     */
    public static String formatUrlMap(Map<String, String> paraMap)
    {
        String buff = "";
        Map<String, String> tmpMap = paraMap;
        try
        {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>()
            {
                @Override
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2)
                {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造URL 键值对的格式
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds)
            {
                if (StringUtils.isNotBlank(item.getKey()))
                {
                    String key = item.getKey();
                    Object val = item.getValue();
                    buf.append(key + "=" + val);
                    buf.append("&");
                }

            }
            buff = buf.toString();
            if (buff.isEmpty() == false)
            {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e)
        {
            return null;
        }
        return buff;
    }

    /**
     * 验证手机号格式
     * @param cellphone 手机号
     * @return
     */
    public static boolean checkCellphone(String cellphone) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,1,2,5-9])|(177))\\d{8}$";
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(cellphone);
        return matcher.matches();
    }

    /**
     * 校验是否通过MD5加密
     * @param str  字符串
     * @return
     */
    public static boolean checkIsEncode(String str){
        if(StringUtils.isNotBlank(str)) {
            return str.length() == 32;
        }else {
            return false;
        }
    }

    public static void main(String[] args) {
//        String domainUrl = "http://image.51huihuahua.com/images/bank/zg_bank@";
//        System.out.println(StringHandleUtils.getDomainAddress("www.taobao.com", domainUrl));
//        System.out.println(prefixHttp.length() +" 截取 ："+prefixHttp.substring(prefixHttp.length()));
//
//        // 字符测试
//        System.out.println("测试 ： "+StringHandleUtils.containsStr("",new String[]{"1","2",""}));

        //18039493398 18589072284
        String phoneNum ="11111111111";
        if(StringUtils.isBlank(phoneNum) || !StringHandleUtils.checkCellphone(phoneNum)){
            System.out.println("手机号码 ============ ： "+checkCellphone(phoneNum));
        }
        System.out.println("手机号码 ++++++++++ ： "+checkCellphone("17717926547"));
    }
}

