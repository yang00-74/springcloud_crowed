package com.goog.crowed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// @EnableEurekaServer ’Î∂‘eureka @EnableDiscoveryClientÕ®”√

@EnableDiscoveryClient
@SpringBootApplication
public class CrowedMainType {

	public static void main(String[] args) {
		SpringApplication.run(CrowedMainType.class, args);
	}
}
