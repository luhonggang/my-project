package com.renqi.market.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 17/6/7.
 * 短信API产品的DEMO程序,工程中包含了一个SmsDemo类，直接通过
 * 执行main函数即可体验短信产品API功能(只需要将AK替换成开通了云通信-短信产品功能的AK即可)
 * 工程依赖了2个jar包(存放在工程的libs目录下)
 * 1:aliyun-java-sdk-core.jar
 * 2:aliyun-java-sdk-dysmsapi.jar
 * 备注:Demo工程编码采用UTF-8
 * 国际短信发送请勿参照此DEMO
 * @author luhonggang
 * @date 2018/9/9
 * @since 1.0
 */
public class MobileCodeUtil {
    private static final String SUCCESS_CODE  = "OK";
    private static final String SUCCESS_STATE = "200";
    /**
     * 判斷是否包含字母
     * @param strContent
     * @return
     */
    public static boolean ContainsStr(String strContent) {
        String regex = ".*[a-zA-Z]+.*";
        Matcher m = Pattern.compile(regex).matcher(strContent);
        return m.matches();
    }

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAIrFGn3of7tQau";
    static final String accessKeySecret = "vMyBIv87ghagohlFboqxZtjU6ChOjT";
    private static Map<String,String> mobileCode = new HashMap<String,String>();
    public static Map<String,String> sendSms(String mobile,String templateId) throws ClientException {

        int mobile_code = (int)((Math.random()*9+1)*100000);
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(mobile);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("人气通");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateId);
//        switch (templateId){
//            case "SMS_77560073":
//        }
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        // {"name":"Tom", "code":"123"}
        request.setTemplateParam("{\"code\":\""+mobile_code+"\"}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            mobileCode.put("code",mobile_code+"");
            mobileCode.put("msg","短信发送成功");
        }else{
            mobileCode.put("code",null);
            mobileCode.put("msg","短信发送失败");
        }
        return mobileCode;
    }


    public static QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber("");
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }


    /**
     * 用户发布任务的时候发送短信通知
     * @param map  手机号码 短信模板 短信参数
     * @return     Map<String,String>
     * @throws ClientException
     */
    public static Map<String,String> sendSmsTask(Map<String,String> map) throws ClientException {

        int mobile_code = (int)((Math.random()*9+1)*100000);
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(map.get("ownPhone"));
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("人气通");
        //必填:短信模板-可在短信控制台中找到
        String templateId = map.get("templateId");
        switch (templateId){
            case "SMS_150575604":
                request.setTemplateCode(templateId);
                request.setTemplateParam("{\"phone\":\""+map.get("phone")+"\"}");
                break;
            case "":
                request.setTemplateCode(templateId);
                request.setTemplateParam("{\"code\":\""+mobile_code+"\"}");
                break;
            default:
                request.setTemplateCode(templateId);
                request.setTemplateParam("{\"code\":\""+mobile_code+"\"}");
                break;
        }

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals(SUCCESS_CODE)) {
            mobileCode.clear();
            mobileCode.put("code",SUCCESS_STATE);
            mobileCode.put("msg","短信发送成功");
        }else{
            mobileCode.put("code",null);
            mobileCode.put("msg","短信发送失败");
        }
        return mobileCode;
    }

    public static void main(String[] args) throws ClientException, InterruptedException {
        //发短信
//        SendSmsResponse response = sendSms("","");
//        System.out.println("短信接口返回的数据----------------");
//        System.out.println("Code=" + response.getCode());
//        System.out.println("Message=" + response.getMessage());
//        System.out.println("RequestId=" + response.getRequestId());
//        System.out.println("BizId=" + response.getBizId());
//
//        Thread.sleep(3000L);
//
//       // 查明细
//        if(response.getCode() != null && response.getCode().equals("OK")) {
//            //mobileCode.put("mobileCode",mobile_code+"");
//            mobileCode.put("msg",response.getCode());
//            QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId());
//            System.out.println("短信明细查询接口返回数据----------------");
//            System.out.println("Code=" + querySendDetailsResponse.getCode());
//            System.out.println("Message=" + querySendDetailsResponse.getMessage());
//            int i = 0;
//            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
//            {
//                System.out.println("SmsSendDetailDTO["+i+"]:");
//                System.out.println("Content=" + smsSendDetailDTO.getContent());
//                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
//                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
//                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
//                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
//                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
//                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
//                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
//            }
//            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
//            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
//        }

    }
}
