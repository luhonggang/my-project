package com.renqi.market.controller;

import com.renqi.market.common.BaseResultMsg;
import com.renqi.market.exception.CheckBaseException;
import com.renqi.market.exception.GlobalException;
import com.renqi.market.util.SystemCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;


/**
 * 全局处理cotroller层面的异常，比如 参数校验失败等等
 * @author luhonggang
 * @date 2018/9/23
 * @since 1.0
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {
    /**
     * 处理Controller类异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler({RuntimeException.class})
    public BaseResultMsg<Object> exceptionHandler(RuntimeException e) {

        log.error("全局捕获异常###exceptionHandler()### ERROR:", e.getMessage(),e);
        StackTraceElement stackTraceElement= e.getStackTrace()[0];
        log.error("File="+stackTraceElement.getFileName());
        log.error("Line="+stackTraceElement.getLineNumber());
        log.error("Method="+stackTraceElement.getMethodName());
        if(e instanceof CheckBaseException){
            CheckBaseException error = (CheckBaseException) e;
            return new BaseResultMsg<>(error.getcode(),error.getmessage());
        }
        if(e instanceof GlobalException){
            GlobalException error = (GlobalException) e;
            return new BaseResultMsg<>(error.getCode(),error.getErrorMessage());
        }else {
            log.error(e.getMessage());
            return new BaseResultMsg<>(SystemCode.SYSTEM_ERROR.getCode(),SystemCode.SYSTEM_ERROR.getMsg());
        }
    }

    /**
     * 当使用@Validated验证参数的时候拦截异常
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler({ConstraintViolationException.class,MethodArgumentNotValidException.class})
    public BaseResultMsg handException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        return new BaseResultMsg(SystemCode.PARAM_VALID_EXCEPTION.getCode(),formatMessage(constraintViolations));
    }

    /**
     * 格式化验证信息
     * @param constraintViolations
     * @return
     */
    private String formatMessage(Set<ConstraintViolation<?>> constraintViolations){
        StringBuilder str = new StringBuilder(SystemCode.PARAM_VALID_EXCEPTION.getMsg());
        if(constraintViolations==null || constraintViolations.size()<1){
            return str.toString();
        }

        int i = 0;
        str.append("：[");
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            str.append(constraintViolation.getMessage());
            if(i<constraintViolations.size()-1){
                str.append("； ");
            }
            i++;
        }
        str.append("]");
        return str.toString();
    }
}
