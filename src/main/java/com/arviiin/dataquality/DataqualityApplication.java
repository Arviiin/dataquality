package com.arviiin.dataquality;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@MapperScan("com.arviiin.dataquality.mapper")//这里不能用这个，文件夹下有不是mapper的
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//禁止自动根据配置文件创建SqlSessionFactory实例
//@SpringBootApplication
public class DataqualityApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataqualityApplication.class, args);
	}

}

