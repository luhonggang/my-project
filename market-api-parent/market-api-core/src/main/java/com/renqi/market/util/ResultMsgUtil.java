package com.renqi.market.util;

import com.renqi.market.common.BaseCustomer;
import com.renqi.market.common.BaseResultMsg;
import java.io.Serializable;

/**
 * @author luhonggang
 * @date 2018/9/8
 * @since 1.0
 */
public class ResultMsgUtil<T> implements Serializable {

    /**
     * 返回消息
     * @param resultMsg 返回给客户端的信息对象
     * @param code      返回码
     * @param msg       返回信息
     * @param data      返回数据
     * @return          BaseResultMsg
     */
    public static BaseResultMsg setResponse(BaseResultMsg resultMsg,String code,String msg,BaseCustomer data){
        resultMsg.setCode(code);
        resultMsg.setMsg(msg);
        resultMsg.setData(data);
        return  resultMsg;
    }
    /**
     * 返回消息
     * @param resultMsg 返回给客户端的信息对象
     * @param code      返回码
     * @param msg       返回信息
     * @return          BaseResultMsg
     */
    public static BaseResultMsg setCodeMsg(BaseResultMsg resultMsg,String code,String msg){
        resultMsg.setCode(code);
        resultMsg.setMsg(msg);
        return  resultMsg;
    }
}
