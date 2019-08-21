package com.goog.crowed;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// @EnableEurekaServer ���eureka @EnableDiscoveryClientͨ��

@MapperScan("com.goog.crowed.mapper")
@EnableDiscoveryClient 
@SpringBootApplication
public class CrowedMainType {

	public static void main(String[] args) {
		SpringApplication.run(CrowedMainType.class, args);
	}
}
