package com.renqi.market.security;

//import com.renqi.market.security.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 *  实现完全自己控制的MVC配置
 */
@Configuration
@EnableWebMvc
@SuppressWarnings("all")
public class WebForWardInterceptor extends WebMvcConfigurerAdapter /*implements HttpMessageConverter*/{

    /**
     * 配置CORS 跨域请求设置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //允许全部请求跨域
        registry.addMapping("/**");
    }

    /**
     * 配置拦截器
     * InterceptorRegistry 内的addInterceptor需要一个实现HandlerInterceptor接口的拦截器实例，addPathPatterns方法用于设置拦截器的过滤路径规则。
     * excludePathPatterns方法用于设置拦截器排除的路径规则
     *
     * @param registry
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //添加拦截器 拦截所有的请求 并排除指定的路由
//        registry.addInterceptor(new JwtInterceptor()).addPathPatterns("/**")
//                .excludePathPatterns("/customer/login");
//        //.excludePathPatterns("/page/login","/customer/login","/customer/mobileCode","/page/index");
//    }

    /**
     * todo 视图  统一管理
     * 视图控制器配置
     * 最经常用到的就是"/"、"/index"路径请求时不通过@RequestMapping配置，而是直接通过配置文件映射指定请求路径到指定View页面
     * 当然也是在请求目标页面时不需要做什么数据处理才可以这样使用
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/").setViewName("/index");
        registry.addViewController("/page/login.html").setViewName("/login");
        registry.addViewController("/page/index.html").setViewName("/index");

    }

    /**
     * 自定义静态资源访问
     * 配置静态访问资源
     * 浏览器访问路由 ： http://localhost:8081/my/shanghai.png 即可访问资源目录下自定义的图片
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/page/**").addResourceLocations("classpath:/static/");
               // .addResourceLocations("classpath*:/static/");
        registry.addResourceHandler("/page/login/**").addResourceLocations("classpath:/template/");
       // registry.addResourceHandler("/page/img/**").addResourceLocations("classpath*:/static/");
        super.addResourceHandlers(registry);
    }
}
