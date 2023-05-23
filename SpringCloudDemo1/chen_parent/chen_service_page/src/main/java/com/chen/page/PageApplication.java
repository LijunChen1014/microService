package com.chen.page;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
//@EnableEurekaClient
@EnableDiscoveryClient //将当前项目表示为注册中心的客户端，向注册中心注册，可以在所有服务注册中心环境下使用，如Nacos 等
public class PageApplication {
    public static void main(String[] args) {
        SpringApplication.run(PageApplication.class,args);
    }

    //像容器中注入RestTemplate，封装了HttpClient
    @Bean
    @LoadBalanced //启用请求负载均衡
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }
}
