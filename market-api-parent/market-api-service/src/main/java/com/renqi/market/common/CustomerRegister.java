package com.renqi.market.common;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
/**
 * 用户注册参数类
 * @author luhonggang
 * @date 2018/9/9
 * @since 1.0
 */
@Data
public class CustomerRegister implements Serializable {
    private String userName;
    @NotNull(message="mobile")
    private String mobile;
    @NotNull(message="passWord不可为空")
    private String passWord;
    @NotNull(message="mobileCode不可为空")
    private String mobileCode;
}
