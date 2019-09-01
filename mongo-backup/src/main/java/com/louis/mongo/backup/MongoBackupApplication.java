package com.louis.mongo.backup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.louis.mongo"})
public class MongoBackupApplication {
    public static void main(String[] args) {
        SpringApplication.run(MongoBackupApplication.class, args);
    }
}
