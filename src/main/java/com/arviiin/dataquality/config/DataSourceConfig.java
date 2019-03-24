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
    @ConfigurationProperties(prefix = "spring.datasource.one")
    DataSource dsOne (){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix="spring.datasource.two")
    DataSource dsTwo(){
        return DruidDataSourceBuilder.create().build();
    }
}
