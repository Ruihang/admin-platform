package com.louis.mongo.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "mongo-producer", fallback = MongoProducerHystrix.class)
public interface MongoProducerService {

    @RequestMapping("/hello")
    public String hello();

}
