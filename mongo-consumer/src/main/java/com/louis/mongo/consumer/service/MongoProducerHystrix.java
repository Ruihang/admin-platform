package com.louis.mongo.consumer.service;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
public class MongoProducerHystrix implements MongoProducerService {

    @Override
    @RequestMapping("/hello")
    public String hello() {
        return "sorry, hello service call failed.";
    }
}
