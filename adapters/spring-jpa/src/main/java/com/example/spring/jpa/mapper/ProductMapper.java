package com.example.spring.jpa.mapper;

import com.example.models.Product;
import com.example.spring.jpa.models.MerchantProductJpa;
import com.example.spring.jpa.models.ProductJpa;

import java.util.stream.Collectors;

public class ProductMapper {

    public static Product asProductDto(ProductJpa in) {
        Product productDto = new Product();

        productDto.setId(in.getId());
        productDto.setHeight(in.getHeight());
        productDto.setLabel(in.getLabel());
        productDto.setCreateDate(in.getCreateDate());
        productDto.setWeight(in.getWeight());
        productDto.setUnitPrice(in.getUnitPrice());

        return productDto;
    }

    public static ProductJpa asProductEntity(Product in) {
        ProductJpa productEntity = new ProductJpa();

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
