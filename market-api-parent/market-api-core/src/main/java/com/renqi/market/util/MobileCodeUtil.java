package com.renqi.market.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author luhonggang
 * @date 2018/9/9
 * @since 1.0
 */
public class MobileCodeUtil {
    private static Map<String,String> mobileCode = new HashMap<String,String>();
    private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
    public static Map getMobileCode(String mobile ,String modelContent){
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(Url);
        client.getParams().setContentCharset("UTF-8");
        method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
        int mobile_code = (int)((Math.random()*9+1)*100000);
        String content = "";
        // String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");
        if(MobileCodeUtil.ContainsStr(modelContent)){// 若包含字母
            content = modelContent.replaceAll("ABCD",mobile_code+"");
            NameValuePair[] data = {//提交短信
                    new NameValuePair("account", "C08711198"), //查看用户名请登录用户中心->验证码、通知短信->帐户及签名设置->APIID
                    new NameValuePair("password", "3b052a11b32c6007836f532b60ec0a67"),  //查看密码请登录用户中心->验证码、通知短信->帐户及签名设置->APIKEY
                    new NameValuePair("mobile", mobile),
                    new NameValuePair("content", content),
            };
            method.setRequestBody(data);
            Document doc = null;
            Element root = null;
            try {
                client.executeMethod(method);
                String SubmitResult =method.getResponseBodyAsString();
                doc = DocumentHelper.parseText(SubmitResult);
                root = doc.getRootElement();
            } catch (HttpException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            mobileCode.put("mobileCode",mobile_code+"");
            mobileCode.put("msg",root.elementText("code"));
        }else{
            mobileCode.put("mobileCode","");
            mobileCode.put("msg","模板内容不符合规范,请检查");
        }
        return  mobileCode;
    }

    /**
     * 判斷是否包含字母
     * @param strContent
     * @return
     */
    public static boolean ContainsStr(String strContent) {
        String regex=".*[a-zA-Z]+.*";
        Matcher m= Pattern.compile(regex).matcher(strContent);
        return m.matches();
    }

}
