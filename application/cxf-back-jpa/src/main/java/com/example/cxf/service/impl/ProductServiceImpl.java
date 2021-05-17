package com.example.cxf.service.impl;

import com.example.cxf.mapper.ProductMapper;
import com.example.cxf.models.ProductCxf;
import com.example.cxf.service.ProductService;
import com.example.spring.config.jpa.SpringConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private SpringConfig springConfig;
    
    @Override
    public ProductCxf getProduct(ProductCxf product) {
        return ProductMapper.asProductDto(springConfig.findProductByLabel().process(product.getLabel()));
    }

    @Override
    public ProductCxf createProduct(ProductCxf product) {
        return ProductMapper.asProductDto(springConfig.createProduct().process(ProductMapper.asProductEntity(product)));
    }

    @Override
    public ProductCxf updateProduct(ProductCxf product) {
        return ProductMapper.asProductDto(springConfig.updateProduct().process(ProductMapper.asProductEntity(product)));
    }

    @Override
    public String deleteProduct(ProductCxf product) {
        springConfig.deleteProduct().process(ProductMapper.asProductEntity(product));
        return "Product has been deleted with success";
    }
}
