//package com.renqi.market.security;
//
//import com.alibaba.fastjson.JSON;
//import com.renqi.market.common.BaseResult;
//import com.renqi.market.exception.HandleTokenException;
//import com.renqi.market.util.JwtTokenUtil;
//import com.renqi.market.util.Separator;
//import com.renqi.market.util.SystemCode;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 拦截器 实现过滤请求及校验token
// *
// * @author luhonggang
// * @date 2018/9/8
// * @since 1.0
// */
//public class JwtInterceptor extends HandlerInterceptorAdapter {
//    private final static Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);
//    @Value("${token.notCheck}")
//    private String notCheckToken;
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String requestURI = ((HttpServletRequest) request).getRequestURI();
//        System.out.println("访问的路由 ： " + requestURI);
//        Map<String, String> msgMap = new HashMap<>();
//        if (requestURI.startsWith("/my") || requestURI.endsWith("/login")) {
//            System.out.println("++++++++ 通过 ++++++++");
//            return true;
//        }
//        // 过滤不需要token的uri
//        if (StringUtils.isNotBlank(notCheckToken)) {
//            String[] notCheckUrls = StringUtils.split(notCheckToken, Separator.COMMA.getValue());
//            for (String notCheckUrl : notCheckUrls) {
//                if (StringUtils.equals(notCheckUrl, requestURI)) {
//                    return true;
//                }
//            }
//        }
//        String token = request.getHeader("token");
//        if (StringUtils.isEmpty(token)) {
//            BaseResult result = new BaseResult(SystemCode.TOKEN_IS_EMPTY.getCode(), SystemCode.TOKEN_IS_EMPTY.getMsg());
//            handleMsgResponse(result, response);
//            return false;
//        } else {
//            //取得token
//            //String token = authHeader.substring(7);
//            try {
//                // todo  除了用户登录外，其他的业务请求接口均要判断token的有效期和token的有效性
//                JwtTokenUtil.checkTokenIsExpiredTime(token);
//            } catch (HandleTokenException e) {
//                handleMsgResponse(new BaseResult(SystemCode.TOKEN_IS_INVALID.getCode(), SystemCode.TOKEN_IS_INVALID.getMsg()), response);
//                return false;
//            } catch (Exception e) {
//                handleMsgResponse(new BaseResult(SystemCode.TOKEN_IS_ERROR.getCode(), SystemCode.TOKEN_IS_ERROR.getMsg()), response);
//                return false;
//            }
//        }
//        return true;
//    }
//
//    /**
//     * 异常处理封装类
//     * @param result   返回的消息
//     * @param response HttpServletResponse
//     * @throws IOException
//     */
//    private void handleMsgResponse(BaseResult result, HttpServletResponse response) throws IOException {
//        response.reset();
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        out.print(JSON.toJSON(result));
//        out.flush();
//        out.close();
//    }
//}
