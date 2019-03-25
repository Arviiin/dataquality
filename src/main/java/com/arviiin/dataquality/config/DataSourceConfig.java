package com.arviiin.dataquality.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created with IDEA
 * 关于多数据源，复杂的就直接上分布式数据库中间件，简单的再考虑多数据源。
 * @Author: jlzhuang
 * @Date: 2019/3/24
 * @Version 1.0.0
 */

@Configuration
public class DataSourceConfig {

    @Bean
    //@Primary//指定主要实现,一个接口有多个实现时,用@Autowired引入时,可以直接引入带有@Primary 注解的实现，不然直接使用@Autowired会报错，
    // 当然可以不加@Primary  使用其他方式引入如@Resource(name = "dsOne")等方式。
    @ConfigurationProperties(prefix = "spring.datasource.one")
    public DataSource dsOne (){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix="spring.datasource.two")
    public DataSource dsTwo(){
        return DruidDataSourceBuilder.create().build();
    }
}
