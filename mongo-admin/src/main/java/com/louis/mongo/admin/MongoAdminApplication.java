package com.louis.mongo.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.louis.mongo"})
public class MongoAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoAdminApplication.class, args);
	}

}
