package com.example.cxf.service.impl;

import com.example.cxf.mapper.MerchantMapper;
import com.example.cxf.models.MerchantCxf;
import com.example.cxf.service.MerchantService;
import com.example.spring.config.jpa.SpringJpaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private SpringJpaConfig springJpaConfig;

    @Override
    public MerchantCxf getMerchant(MerchantCxf merchant) {
        return MerchantMapper.asMerchantDto(springJpaConfig.findMerchantByName().process(merchant.getName()));
    }

    @Override
    public MerchantCxf createMerchant(MerchantCxf merchant) {
        return MerchantMapper.asMerchantDto(springJpaConfig.createMerchant().process(MerchantMapper.asMerchantEntity(merchant)));
    }

    @Override
    public MerchantCxf updateMerchant(MerchantCxf merchant) {
        return MerchantMapper.asMerchantDto(springJpaConfig.updateMerchant().process(MerchantMapper.asMerchantEntity(merchant)));

    }

    @Override
    public String deleteMerchant(MerchantCxf merchant) {
        springJpaConfig.deleteMerchant().process(MerchantMapper.asMerchantEntity(merchant));
        return "Merchant has been deleted with success";
    }
}
