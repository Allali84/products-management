package com.example.spring.boot.ws;

import com.example.gs_producing_web_service.*;
import com.example.spring.config.jpa.SpringConfig;
import com.example.spring.boot.mapper.MerchantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class MerchantEndpoint {
    private static final String NAMESPACE_URI = "http://example.com/gs-producing-web-service";

    private SpringConfig springConfig;

    @Autowired
    public MerchantEndpoint(SpringConfig springConfig) {
        this.springConfig = springConfig;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMerchantRequest")
    @ResponsePayload
    public GetMerchantResponse getMerchant(@RequestPayload GetMerchantRequest request) {
        GetMerchantResponse response = new GetMerchantResponse();
        response.setMerchant(MerchantMapper.asMerchantDto(springConfig.findMerchantByName().process(request.getName())));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createMerchantRequest")
    @ResponsePayload
    public CreateMerchantResponse createMerchant(@RequestPayload CreateMerchantRequest request) {
        CreateMerchantResponse response = new CreateMerchantResponse();
        response.setMerchant(MerchantMapper.asMerchantDto(springConfig.createMerchant().process(MerchantMapper.asMerchantEntity(request.getMerchant()))));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateMerchantRequest")
    @ResponsePayload
    public UpdateMerchantResponse updateMerchant(@RequestPayload UpdateMerchantRequest request) {
        UpdateMerchantResponse response = new UpdateMerchantResponse();
        response.setMerchant(MerchantMapper.asMerchantDto(springConfig.updateMerchant().process(MerchantMapper.asMerchantEntity(request.getMerchant()))));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteMerchantRequest")
    @ResponsePayload
    public DeleteMerchantResponse deleteMerchant(@RequestPayload DeleteMerchantRequest request) {
        DeleteMerchantResponse response = new DeleteMerchantResponse();
        springConfig.deleteMerchant().process(MerchantMapper.asMerchantEntity(request.getMerchant()));
        response.setMessage("Merchant has been deleted with success");
        return response;
    }
}
