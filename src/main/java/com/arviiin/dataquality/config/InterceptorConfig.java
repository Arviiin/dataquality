package com.arviiin.dataquality.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfigurerAdapter已经过时，官方推荐下面的方式
 * 拦截器的配置文件
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    /*@Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequredInterceptor loginRequredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequredInterceptor).addPathPatterns("/user/*");//此拦截器用到hostHolder所以一定要放在上面拦截器的后面，因为是在上面拦截器里面设置的hostHolder
    }*/
}
