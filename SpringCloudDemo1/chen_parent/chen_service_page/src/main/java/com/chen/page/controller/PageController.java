package com.chen.page.controller;


import com.chen.common.pojo.Products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/page")
public class PageController {
    @Autowired
    private RestTemplate restTemplate;
    //服务注册中心的客户端对象
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/getProduct/{id}")
    public Products getProduct(@PathVariable Integer id){
//        //获得lagou_service_product在服务注册中心的服务列表
//        List<ServiceInstance> instances = discoveryClient.getInstances("chen-service-product");
//        //2.获得实例集合中的第一个
//        ServiceInstance instance = instances.get(0);
//        String host = instance.getHost();
//        int port = instance.getPort();   #只有一个service-product时 使用
        //拼接url
        String url = "http://chen-service-product/product/query/"+id;
        //发送Http给商品微服务，将id传递过去，获取到id所对应的products对象

        return restTemplate.getForObject(url,Products.class);
    }

    @GetMapping("/loadProductPort")
    public String getProductServerPort(){
//        ServiceInstance serviceInstance = discoveryClient.getInstances("chen-service-product").get(0);
//        String host = serviceInstance.getHost();
//        int port = serviceInstance.getPort(); //因为chen-service-product是集群，要实现负载均衡，就不需要获取host
        String url = "http://chen-service-product/service/port";
        String result = restTemplate.getForObject(url,String.class);
        return result;

    }

}
