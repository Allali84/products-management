package com.example.spring.hibernate.mapper;


import com.example.models.Product;
import com.example.spring.hibernate.models.MerchantProductJpa;
import com.example.spring.hibernate.models.ProductHibernate;

import java.util.stream.Collectors;

public class ProductMapper {

    public static Product asProductDto(ProductHibernate in) {
        Product productDto = new Product();

        productDto.setId(in.getId());
        productDto.setHeight(in.getHeight());
        productDto.setLabel(in.getLabel());
        productDto.setCreateDate(in.getCreateDate());
        productDto.setWeight(in.getWeight());
        productDto.setUnitPrice(in.getUnitPrice());

        return productDto;
    }

    public static ProductHibernate asProductEntity(Product in) {
        ProductHibernate productEntity = new ProductHibernate();

        productEntity.setId(in.getId());
        productEntity.setHeight(in.getHeight());
        productEntity.setLabel(in.getLabel());
        productEntity.setCreateDate(in.getCreateDate());
        productEntity.setWeight(in.getWeight());
        productEntity.setUnitPrice(in.getUnitPrice());
        productEntity.setMerchants(
                in.getMerchants()
                        .stream()
                        .map(merchant -> new MerchantProductJpa(MerchantMapper.asMerchantEntity(merchant), productEntity))
                        .collect(Collectors.toSet())
        );

        return productEntity;
    }
}
