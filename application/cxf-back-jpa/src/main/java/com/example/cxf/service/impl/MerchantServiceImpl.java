package com.example.cxf.service.impl;

import com.example.cxf.mapper.MerchantMapper;
import com.example.cxf.models.MerchantCxf;
import com.example.cxf.service.MerchantService;
import com.example.spring.config.jpa.SpringConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private SpringConfig springConfig;

    @Override
    public MerchantCxf getMerchant(MerchantCxf merchant) {
        return MerchantMapper.asMerchantDto(springConfig.findMerchantByName().process(merchant.getName()));
    }

    @Override
    public MerchantCxf createMerchant(MerchantCxf merchant) {
        return MerchantMapper.asMerchantDto(springConfig.createMerchant().process(MerchantMapper.asMerchantEntity(merchant)));
    }

    @Override
    public MerchantCxf updateMerchant(MerchantCxf merchant) {
        return MerchantMapper.asMerchantDto(springConfig.updateMerchant().process(MerchantMapper.asMerchantEntity(merchant)));

    }

    @Override
    public String deleteMerchant(MerchantCxf merchant) {
        springConfig.deleteMerchant().process(MerchantMapper.asMerchantEntity(merchant));
        return "Merchant has been deleted with success";
    }
}
