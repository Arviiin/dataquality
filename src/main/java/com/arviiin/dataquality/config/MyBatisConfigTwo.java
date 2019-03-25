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
 * 用来存储和待量数据相关的
 * @Author: jlzhuang
 * @Date: 2019/3/24
 * @Version 1.0.0
 */
@Slf4j//加了这个就可以直接使用创建好了的LoggerFactory的log对象
@Configuration
@MapperScan(basePackages = "com.arviiin.dataquality.mapperTwo",sqlSessionFactoryRef = "sqlSessionFactoryTwo",
        sqlSessionTemplateRef = "sqlSessionTemplateTwo")
public class MyBatisConfigTwo {

    @Resource(name = "dsTwo")
    DataSource dsTwo;

    @Bean
    public SqlSessionFactory sqlSessionFactoryTwo(){
        SqlSessionFactory sessionFactory = null;
        try {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(dsTwo);
            bean.setTypeAliasesPackage("com.arviiin.dataquality.model");
            //添加驼峰命名法映射
            org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
            configuration.setMapUnderscoreToCamelCase(true);
            bean.setConfiguration(configuration);
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapperTwo/*.xml"));
            sessionFactory = bean.getObject();
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("sqlSessionFactoryTwo 创建成功。");
        return sessionFactory;
    }

    @Bean
    SqlSessionTemplate sqlSessionTemplateTwo(){
        return new SqlSessionTemplate(sqlSessionFactoryTwo());
    }
}
