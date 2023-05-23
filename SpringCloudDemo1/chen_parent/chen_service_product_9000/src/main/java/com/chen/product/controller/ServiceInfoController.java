package com.chen.product.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class ServiceInfoController {
    @Value("${server.port}")
    private String port;
    @GetMapping("/port")
    public String getPort() {
        //模拟延时，使得请求超时，触发熔断
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return port;
    }
}
