package com.goog.crowed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

// @EnableDiscoveryClientÕ®”√

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class CrowedMainType {

	public static void main(String[] args) {
		SpringApplication.run(CrowedMainType.class, args);
	}
}
