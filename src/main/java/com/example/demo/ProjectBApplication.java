package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

// @SpringBootApplication

// Security 로그인 페이지 비활성화 처리 해줬습니다.
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ProjectBApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectBApplication.class, args);
	}

}
