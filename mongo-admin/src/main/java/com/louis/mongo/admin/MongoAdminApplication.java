package com.louis.mongo.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.louis.mongo"})
public class MongoAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoAdminApplication.class, args);
	}

}
