package com.louis.mongo.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MongoProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoProducerApplication.class, args);
	}

}
