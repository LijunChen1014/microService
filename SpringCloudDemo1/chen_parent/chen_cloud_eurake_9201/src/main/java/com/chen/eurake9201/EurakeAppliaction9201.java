package com.chen.eurake9201;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurakeAppliaction9201 {
    public static void main(String[] args) {
        SpringApplication.run(EurakeAppliaction9201.class);
    }
}
