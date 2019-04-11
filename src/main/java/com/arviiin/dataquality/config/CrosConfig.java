package com.arviiin.dataquality.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 解决前后端跨域问题
 * 由于jdk8的改动，接口中可以有默认实现方法了，所以WebMvcConfigurerAdapter类的作用就不复存在
 * 继承WebMvcConfigurerAdapter已经过时，官方推荐下面的方式
 *
 * 注意若引入spring security后，跨域问题又变得不一样了。
 **/
@Configuration
public class CrosConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }
}
