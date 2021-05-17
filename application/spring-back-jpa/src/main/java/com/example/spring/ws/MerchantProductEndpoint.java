package com.example.spring.ws;

import com.example.gs_producing_web_service.*;
import com.example.spring.config.jpa.SpringConfig;
import com.example.spring.mapper.MerchantMapper;
import com.example.spring.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class MerchantProductEndpoint {
    private static final String NAMESPACE_URI = "http://example.com/gs-producing-web-service";

    private SpringConfig springConfig;

    @Autowired
    public MerchantProductEndpoint(SpringConfig springConfig) {
        this.springConfig = springConfig;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "associateMerchantProductRequest")
    @ResponsePayload
    public AssociateMerchantProductResponse getMerchant(@RequestPayload AssociateMerchantProductRequest request) {
        AssociateMerchantProductResponse response = new AssociateMerchantProductResponse();
        Merchant merchant = request.getMerchant();
        Product product = request.getProduct();
        com.example.models.Merchant returnedMerchant = springConfig.associateWithProduct().process(
                MerchantMapper.asMerchantEntity(merchant),
                ProductMapper.asProductEntity(product)
        );
        response.setMerchant(MerchantMapper.asMerchantDto(returnedMerchant));
        return response;
    }
}
