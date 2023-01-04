package com.bitstudy.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan // config > TheymeleafConfig 파일 사용
public class ProjectBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectBoardApplication.class, args);
	}

}
