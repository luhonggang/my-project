package com.bootdo.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author bootdo
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils{

    private static final String[] pcTypeList = new String[]{"1","2","3","4","5","6","7","11","13"};

    private static final String[] templateNameList;
    private static final String[] templateList;
    static {
        templateList = new String[]{
                "80秒 - 120秒",
                "130秒 - 170秒",
                "190秒 - 230秒",
                "260秒 - 300秒",
                "50秒 - 80秒",
                "100秒 - 130秒",
                "160秒 - 190秒",
                "230秒 - 260秒"
        };
        templateNameList = new String[]{
          "浏览商品详情页,",
          "货比三家,",
          "浏览商品评价,",
          "浏览关联商品",
          "浏览商品详情页,",
          "货比三家,",
          "浏览商品评价,",
          "浏览商品详情页"
        };
    }

    /**
     * 获取模板名称
     * @param arrayList
     * @return
     */
    public static String getTemplateName(String[] arrayList){
        String templateName = "";
        for (int i = 0; i < arrayList.length; i++) {
            if(isNumeric(arrayList[0])) {
                templateName += templateNameList[Integer.parseInt(arrayList[0]) - 1];
            }else {
                continue;
            }
        }
        if(templateName.endsWith(",")){
            templateName = templateName.substring(0,templateName.length()-1);
        }
        return  templateName;
    }
    /**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches()){
            return false;
        }
        return true;
    }

    /**
     * 获得浏览时长
     * @param templateId 模板ID
     * @return
     */
    public static String getBowerTime(String templateId){
        return templateList[(Integer.parseInt(templateId)-1)];
    }
    private static boolean containStr(String[] arrayList,String str){
        boolean isContains = false;
        for (int i = 0; i < arrayList.length ; i++)
          if(arrayList[i].equalsIgnoreCase(str)){
              isContains =  true;
              break;
          }
          return isContains;
    }


    /**
     * 获取任务的类型
     * @param type
     * @return
     */
    public static String getTaskType(String type){
        String typeName = "";
        switch (type){
                case "1":typeName = "PC端匿名访问";break;
                case "2":typeName = "PC端实名访问";break;
                case "3":typeName = "PC直通车流量";break;
                case "4":typeName = "PC聚划算开团提醒";break;
                case "5":typeName = "手机端匿名访问";break;
                case "6":typeName = "手机端实名访问";break;
                case "7":typeName = "手机端天猫App流量";break;
                case "9":typeName = "其他京东App流量";break;
                case "10":typeName = "猜你喜欢流量";break;
                case "11":typeName = "手机直通车流量";break;
                case "12":typeName = "PC端收藏加购";break;
                case "13":typeName = "手机端收藏加购";break;
                default:typeName = "PC端匿名访问";break;
         }
        return typeName;
    }

}
