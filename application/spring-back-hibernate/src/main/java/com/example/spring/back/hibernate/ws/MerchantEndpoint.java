package com.example.spring.back.hibernate.ws;

import com.example.gs_producing_web_service.*;
import com.example.spring.config.hibernate.SpringHibernateConfig;
import com.example.spring.back.hibernate.mapper.MerchantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class MerchantEndpoint {
    private static final String NAMESPACE_URI = "http://example.com/gs-producing-web-service";

    private SpringHibernateConfig springHibernateConfig;

    @Autowired
    public MerchantEndpoint(SpringHibernateConfig springHibernateConfig) {
        this.springHibernateConfig = springHibernateConfig;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMerchantRequest")
    @ResponsePayload
    public GetMerchantResponse getMerchant(@RequestPayload GetMerchantRequest request) {
        GetMerchantResponse response = new GetMerchantResponse();
        response.setMerchant(MerchantMapper.asMerchantDto(springHibernateConfig.findMerchantByName().process(request.getName())));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createMerchantRequest")
    @ResponsePayload
    public CreateMerchantResponse createMerchant(@RequestPayload CreateMerchantRequest request) {
        CreateMerchantResponse response = new CreateMerchantResponse();
        response.setMerchant(MerchantMapper.asMerchantDto(springHibernateConfig.createMerchant().process(MerchantMapper.asMerchantEntity(request.getMerchant()))));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateMerchantRequest")
    @ResponsePayload
    public UpdateMerchantResponse updateMerchant(@RequestPayload UpdateMerchantRequest request) {
        UpdateMerchantResponse response = new UpdateMerchantResponse();
        response.setMerchant(MerchantMapper.asMerchantDto(springHibernateConfig.updateMerchant().process(MerchantMapper.asMerchantEntity(request.getMerchant()))));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteMerchantRequest")
    @ResponsePayload
    public DeleteMerchantResponse deleteMerchant(@RequestPayload DeleteMerchantRequest request) {
        DeleteMerchantResponse response = new DeleteMerchantResponse();
        springHibernateConfig.deleteMerchant().process(MerchantMapper.asMerchantEntity(request.getMerchant()));
        response.setMessage("Merchant has been deleted with success");
        return response;
    }
}
