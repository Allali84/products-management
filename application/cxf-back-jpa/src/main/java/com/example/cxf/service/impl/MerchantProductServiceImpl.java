package com.example.cxf.service.impl;

import com.example.cxf.mapper.MerchantMapper;
import com.example.cxf.mapper.ProductMapper;
import com.example.cxf.models.MerchantCxf;
import com.example.cxf.models.ProductCxf;
import com.example.cxf.service.MerchantProductService;
import com.example.spring.config.jpa.SpringConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;

@Service
public class MerchantProductServiceImpl implements MerchantProductService {

    @Autowired
    private SpringConfig springConfig;

    @Override
    public MerchantCxf associateMerchantWithProduct(MerchantCxf merchant, ProductCxf product) {
        return MerchantMapper.asMerchantDto(springConfig.associateWithProduct().process(MerchantMapper.asMerchantEntity(merchant), ProductMapper.asProductEntity(product)));
    }
}
