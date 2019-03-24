package com.arviiin.dataquality.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created with IDEA
 *
 * @Author: jlzhuang
 * @Date: 2019/3/24
 * @Version 1.0.0
 */

@Configuration
@MapperScan(basePackages = "com.arviiin.dataquality.mapper",sqlSessionFactoryRef = "sqlSessionFactory1",sqlSessionTemplateRef = "sqlSessionTemplate1")
public class MyBatisConfigOne {
    @Resource(name = "dsOne")
    DataSource dsOne;

    @Bean
    SqlSessionFactory sqlSessionFactory1(){
        SqlSessionFactory sessionFactory = null;
        try {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(dsOne);
            sessionFactory = bean.getObject();
        }catch (Exception e){
            e.printStackTrace();
        }
        return sessionFactory;
    }

    @Bean
    SqlSessionTemplate sqlSessionTemplate1(){
        return new SqlSessionTemplate(sqlSessionFactory1());
    }
}
