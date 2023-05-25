package com.chen.page.feign;

import com.chen.common.pojo.Products;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 自定义feign接口，调用product微服务的所有接口方法都在此进行定义
 */
@FeignClient(name = "chen-service-product",fallback = productFeignFallback.class)
public interface ProductFeign {

    @GetMapping("/product/query/{id}")
    public Products queryById(@PathVariable Integer id);

    @GetMapping("service/port")
    public String getPort();
}
