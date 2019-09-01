package com.louis.mongo.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.louis.mongo"})
public class MongoMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoMonitorApplication.class, args);
	}

}
