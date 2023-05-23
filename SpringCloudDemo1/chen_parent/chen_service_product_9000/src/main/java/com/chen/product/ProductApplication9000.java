package com.chen.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient // 将当前项目作为Eureka client 注册到 Eureka server
@MapperScan("com.chen.product.mapper")
public class ProductApplication9000 {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication9000.class,args);
    }
}
