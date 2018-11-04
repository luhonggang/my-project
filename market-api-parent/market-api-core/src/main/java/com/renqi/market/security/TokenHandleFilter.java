package com.renqi.market.security;

import com.renqi.market.exception.BaseResultException;
import com.renqi.market.exception.HandleTokenException;
import com.renqi.market.exception.NotExistTokenException;
import com.renqi.market.util.JsonUtil;
import com.renqi.market.util.JwtTokenUtil;
import com.renqi.market.util.SystemCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * token 验签 例:获取用户信息 依据token查询用户信息是否存在于当前库
 *
 * @author luhonggang
 * @version 1.8.0
 * @date 2018/9/10 15:53
 */
@WebFilter(urlPatterns = "*.json")
@Component
@SuppressWarnings("all")
public class TokenHandleFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(TokenHandleFilter.class);

    @Value("${springBoot.token.checkUrl}")
    private String checkLoginUrl;
    private Pattern pattern = null;
    private Matcher match = null;
    private boolean flag = false;

    /**
     * 判断是否匹配到URL
     * @param url
     * @return
     */
    public boolean isMactchUrl(String url){
        // 判断含有 url
        flag = false;
        if (null == checkLoginUrl) {
            return flag;
        }
        List<String> urlsList = Arrays.asList(checkLoginUrl.split(","));
        urlsList.forEach(params -> {
            // 匹配成功，跳出循环
            if (url.contentEquals(params)) {
                flag = true;
                return;
            }
        });
        if (flag) {
            return flag;
        } else {
            // 正则匹配
            urlsList.forEach(param -> {
                pattern = Pattern.compile(param);
                match = pattern.matcher(url);
                // 匹配成功，跳出循环
                if (match.matches()) {
                    flag = true;
                    return;
                }

            });
        }
        return flag;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//            httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//            httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
//            httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        String url = httpServletRequest.getServletPath();
        logger.info("+++++++++++ current url +++++++++++ " + url);
        try {
            logger.info("+++++++++ matchTokenUtils +++++++++ : "+checkLoginUrl);
            logger.info("+++++++++ matchTokenUtils +++++++++ : {}",checkLoginUrl);
            if (isMactchUrl(url)) {
                logger.info("+++++++++++ current url is need token +++++++++++ token ");
                // 说明是需要校验token的
                String token = ((HttpServletRequest) request).getHeader("token");
                if (StringUtils.isEmpty(token) || "null".equals(token)) {
                    throw new NotExistTokenException();
                }
                logger.info("++++++++++ token ++++++++++: "+token);
                JwtTokenUtil.checkTokenIsExpiredTime(token);
            }
            logger.info("++++++++++ not token ++++++++++: ");
        } catch (NotExistTokenException e1) {
            logger.error(this + "+++++++++++:token不存在:+++++++++++", e1);
            BaseResultException<?> baseResponse = BaseResultException.fail(SystemCode.TOKEN_IS_EMPTY.getCode(),
                    SystemCode.TOKEN_IS_EMPTY.getMsg());
            this.setResponseData(httpServletResponse, JsonUtil.toJsonString(baseResponse));
            return;
        } catch (HandleTokenException e2) {
            logger.error(this + "+++++++++++:token无效:+++++++++++", e2);
            BaseResultException<?> baseResponse = BaseResultException.fail(SystemCode.EXPIRED_TOKEN.getCode(),
                    SystemCode.EXPIRED_TOKEN.getMsg());
            this.setResponseData(httpServletResponse, JsonUtil.toJsonString(baseResponse));
            return;
        } catch (Exception e) {
            logger.error(this + "+++++++++++:token校验出现未知异常:+++++++++++", e);
            BaseResultException<?> baseResponse = BaseResultException.fail(SystemCode.UNKNOW_EXCEPTION.getCode(),
                    SystemCode.UNKNOW_EXCEPTION.getMsg());
            this.setResponseData(httpServletResponse, JsonUtil.toJsonString(baseResponse));
            return;
        }
        chain.doFilter(httpServletRequest, httpServletResponse);

    }

    /**
     * 返回的信息
     *
     * @param httpServletResponse
     * @param result
     * @throws IOException
     */
    public void setResponseData(HttpServletResponse httpServletResponse, String result) throws IOException {
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        PrintWriter out = httpServletResponse.getWriter();
        out.print(result);
    }

    @Override
    public void destroy() {

    }
}
