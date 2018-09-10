package com.renqi.market.util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author luhonggang
 * @version 1.8.0
 * @date 2018/9/10 15:57
 */
@Configuration
public class MatchTokenUtils {
    private Pattern pattern = null;
    private Matcher match = null;
    private boolean flag = false;
    @Value("${springBoot.token.checkUrl}") // 获取需要token验签的url
    private String checkLoginUrls;
    public boolean isMactchUrl(String url){
        // 判断含有 url
        flag = false;
        if (null == checkLoginUrls) {
            return flag;
        }
        List<String> urlsList = Arrays.asList(checkLoginUrls.split(","));
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

}
