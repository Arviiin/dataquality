package com.arviiin.dataquality.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created with IDEA
 * one用来存储本系统相关的数据
 * @Author: jlzhuang
 * @Date: 2019/3/24
 * @Version 1.0.0
 */

@Configuration
@Slf4j
@MapperScan(basePackages = "com.arviiin.dataquality.mapper",//mapper即是mapperOne
        sqlSessionFactoryRef = "sqlSessionFactoryOne",
        sqlSessionTemplateRef = "sqlSessionTemplateOne")
public class MyBatisConfigOne {

    @Resource(name = "dsOne")
    DataSource dsOne;

    @Bean
    public SqlSessionFactory sqlSessionFactoryOne(){
        SqlSessionFactory sessionFactory = null;
        try {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(dsOne);
            bean.setTypeAliasesPackage("com.arviiin.dataquality.model");
            //添加驼峰命名法映射
            org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
            configuration.setMapUnderscoreToCamelCase(true);
            bean.setConfiguration(configuration);
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/*.xml"));
            sessionFactory = bean.getObject();
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("sqlSessionFactoryOne 创建成功。");
        return sessionFactory;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateOne(){
        return new SqlSessionTemplate(sqlSessionFactoryOne());// 使用上面配置的Factory
    }
}
