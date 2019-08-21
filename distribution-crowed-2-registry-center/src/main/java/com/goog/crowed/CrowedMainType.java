package com.goog.crowed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

// @EnableEurekaServer ’Î∂‘eureka @EnableDiscoveryClientÕ®”√

@EnableEurekaServer 
@SpringBootApplication
public class CrowedMainType {

	public static void main(String[] args) {
		SpringApplication.run(CrowedMainType.class, args);
	}
}
