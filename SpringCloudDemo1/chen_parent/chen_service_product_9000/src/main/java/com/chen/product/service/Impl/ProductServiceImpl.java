package com.chen.product.service.Impl;

import com.chen.common.pojo.Products;
import com.chen.product.mapper.ProductMapper;
import com.chen.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Products queryById(Integer id) {
        return productMapper.selectById(id);
    }
}
