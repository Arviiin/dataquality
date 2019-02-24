package com.arviiin.dataquality;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@MapperScan("com.arviiin.dataquality.mapper")//这里不能用这个，文件夹下有不是mapper的
@SpringBootApplication
public class DataqualityApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataqualityApplication.class, args);
	}

}

