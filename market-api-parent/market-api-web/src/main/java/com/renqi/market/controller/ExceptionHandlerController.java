package com.renqi.market.controller;

import com.renqi.market.common.BaseResultMsg;
import com.renqi.market.exception.GlobalException;
import com.renqi.market.util.SystemCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ExceptionHandlerController {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    /**
     * 处理Controller类异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler({RuntimeException.class})
    public BaseResultMsg<Object> exceptionHandler(RuntimeException e) {

        logger.error("全局捕获异常###exceptionHandler()### ERROR:", e.getMessage(),e);
        StackTraceElement stackTraceElement= e.getStackTrace()[0];
        logger.error("File="+stackTraceElement.getFileName());
        logger.error("Line="+stackTraceElement.getLineNumber());
        logger.error("Method="+stackTraceElement.getMethodName());
        if(e instanceof GlobalException){
            GlobalException error = (GlobalException) e;
            return new BaseResultMsg<>(error.getCode(),error.getErrorMessage());
        }else {
            logger.error(e.getMessage());
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
