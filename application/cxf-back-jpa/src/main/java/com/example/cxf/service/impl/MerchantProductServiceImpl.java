package com.example.cxf.service.impl;

import com.example.cxf.mapper.MerchantMapper;
import com.example.cxf.mapper.ProductMapper;
import com.example.cxf.models.MerchantCxf;
import com.example.cxf.models.ProductCxf;
import com.example.cxf.service.MerchantProductService;
import com.example.spring.config.jpa.SpringJpaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantProductServiceImpl implements MerchantProductService {

    @Autowired
    private SpringJpaConfig springJpaConfig;

    @Override
    public MerchantCxf associateMerchantWithProduct(MerchantCxf merchant, ProductCxf product) {
        return MerchantMapper.asMerchantDto(springJpaConfig.associateWithProduct().process(MerchantMapper.asMerchantEntity(merchant), ProductMapper.asProductEntity(product)));
    }
}
