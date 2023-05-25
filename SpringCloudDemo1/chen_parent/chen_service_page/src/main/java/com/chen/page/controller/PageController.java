package com.chen.page.controller;


import com.chen.common.pojo.Products;

import com.chen.page.feign.ProductFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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
//    @Autowired
//    private RestTemplate restTemplate;
//    //服务注册中心的客户端对象
//    @Autowired
//    private DiscoveryClient discoveryClient;

    @Autowired
    private ProductFeign productFeign;
    @GetMapping("/getProduct/{id}")
    public Products getProduct(@PathVariable Integer id){
        return productFeign.queryById(id);
    }

    @GetMapping("/loadProductPort")
    public String getProductServerPort(){
        return productFeign.getPort();
    }


//restTemplate 请求方式
//    @GetMapping("/getProduct/{id}")
//    public Products getProduct(@PathVariable Integer id){
////        //获得lagou_service_product在服务注册中心的服务列表
////        List<ServiceInstance> instances = discoveryClient.getInstances("chen-service-product");
////        //2.获得实例集合中的第一个
////        ServiceInstance instance = instances.get(0);
////        String host = instance.getHost();
////        int port = instance.getPort();   #只有一个service-product时 使用
//        //拼接url
//        String url = "http://chen-service-product/product/query/"+id;
//        //发送Http给商品微服务，将id传递过去，获取到id所对应的products对象
//
//        return restTemplate.getForObject(url,Products.class);
//    }

    @HystrixCommand(
            // 线程池标识，要保持唯一，不唯一的话就共用了
            //只要在@HystrixCommand中定义了threadPoolKey，就意味着开启了舱壁模式（线程隔离），该方法就会自己维护一个线程池
            threadPoolKey = "loadProductPort", //默认所有的请求共同维护一个线线程池，实际开发：每个方法维护一个线程池        // 线程池标识，要保持唯一，不唯一的话就共用了
            // 线程池细节属性配置
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize",value = "1"), // 并发线程数
                    @HystrixProperty(name="maxQueueSize",value="20") // 等待队列长度，默认值为-1
            },
        // commandProperties熔断的一些细节属性配置
            commandProperties = {
            // 每一个属性都是一个HystrixProperty
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value=
                            "2000") //设置请求的超时时间，一旦请求超过此时间那么都按照超时处理
            }
    )
    @GetMapping("/loadProductPort2")
    public String getProductServerPort2(){
//        ServiceInstance serviceInstance = discoveryClient.getInstances("chen-service-product").get(0);
//        String host = serviceInstance.getHost();
//        int port = serviceInstance.getPort(); //因为chen-service-product是集群，要实现负载均衡，就不需要获取host
//        String url = "http://chen-service-product/service/port";
//        String result = restTemplate.getForObject(url,String.class);
//        return result;
        return productFeign.getPort();

    }

    @HystrixCommand(
            commandProperties = {
                    // 每一个属性都是一个HystrixProperty
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="2000"), //设置请求的超时时间，一旦请求超过此时间那么都按照超时处理
                    //统计窗口时间的设置
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "8000"),
                    //统计窗口内的最小请求数
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
                    //统计窗口内错误请求阈值的设置 50%
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    //自我修复的活动窗口时间
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "3000")

            },
            fallbackMethod = "getProductServerPortFallBack"
    )
    @GetMapping("/loadProductPort3")
    public String getProductServerPort3(){

//        String url = "http://chen-service-product/service/port";
//        String result = restTemplate.getForObject(url,String.class);
//        return result;
        return productFeign.getPort();
    }

    /**
     * 回退方法，当请求触发熔断后执行，补救措施
     * 1,方法形参和原方法保持一致
     * 2.方法的返回值与原方法一致
     */
    public String getProductServerPortFallBack(){
        return "-1"; //兜底数据
    }

}
