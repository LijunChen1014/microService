package com.chen.page.feign;


import com.chen.common.pojo.Products;
import org.springframework.stereotype.Component;

@Component
public class productFeignFallback implements ProductFeign {

    @Override
    public Products queryById(Integer id) {
        return null;  //兜底数据
    }

    @Override
    public String getPort() {
        return "-1";
    }
}
