package com.example.cxf.service.impl;

import com.example.cxf.mapper.ProductMapper;
import com.example.cxf.models.ProductCxf;
import com.example.cxf.service.ProductService;
import com.example.spring.config.jpa.SpringJpaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private SpringJpaConfig springJpaConfig;
    
    @Override
    public ProductCxf getProduct(ProductCxf product) {
        return ProductMapper.asProductDto(springJpaConfig.findProductByLabel().process(product.getLabel()));
    }

    @Override
    public ProductCxf createProduct(ProductCxf product) {
        return ProductMapper.asProductDto(springJpaConfig.createProduct().process(ProductMapper.asProductEntity(product)));
    }

    @Override
    public ProductCxf updateProduct(ProductCxf product) {
        return ProductMapper.asProductDto(springJpaConfig.updateProduct().process(ProductMapper.asProductEntity(product)));
    }

    @Override
    public String deleteProduct(ProductCxf product) {
        springJpaConfig.deleteProduct().process(ProductMapper.asProductEntity(product));
        return "Product has been deleted with success";
    }
}
