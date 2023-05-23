package com.chen.product.service;

import com.chen.common.pojo.Products;

public interface ProductService {
    /**
     * 通过商品Id 查询
     * @param id
     * @return
     */
    public Products queryById(Integer id);
}
