package com.chen.eurake;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurakeAppliaction {
    public static void main(String[] args) {
        SpringApplication.run(EurakeAppliaction.class);
    }
}
