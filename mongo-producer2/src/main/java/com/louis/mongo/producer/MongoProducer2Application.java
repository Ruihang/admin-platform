package com.louis.mongo.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MongoProducer2Application {

	public static void main(String[] args) {
		SpringApplication.run(MongoProducer2Application.class, args);
	}

}
