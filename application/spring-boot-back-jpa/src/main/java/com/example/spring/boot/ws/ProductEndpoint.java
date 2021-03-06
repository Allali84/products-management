package com.example.spring.boot.ws;

import com.example.gs_producing_web_service.*;
import com.example.spring.config.jpa.SpringJpaConfig;
import com.example.spring.boot.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://example.com/gs-producing-web-service";

    private SpringJpaConfig springJpaConfig;

    @Autowired
    public ProductEndpoint(SpringJpaConfig springJpaConfig) {
        this.springJpaConfig = springJpaConfig;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductRequest")
    @ResponsePayload
    public GetProductResponse getProduct(@RequestPayload GetProductRequest request) {
        GetProductResponse response = new GetProductResponse();
        response.setProduct(ProductMapper.asProductDto(springJpaConfig.findProductByLabel().process(request.getLabel())));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createProductRequest")
    @ResponsePayload
    public CreateProductResponse createProduct(@RequestPayload CreateProductRequest request) {
        CreateProductResponse response = new CreateProductResponse();
        response.setProduct(ProductMapper.asProductDto(springJpaConfig.createProduct().process(ProductMapper.asProductEntity(request.getProduct()))));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateProductRequest")
    @ResponsePayload
    public UpdateProductResponse updateProduct(@RequestPayload UpdateProductRequest request) {
        UpdateProductResponse response = new UpdateProductResponse();
        response.setProduct(ProductMapper.asProductDto(springJpaConfig.updateProduct().process(ProductMapper.asProductEntity(request.getProduct()))));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteProductRequest")
    @ResponsePayload
    public DeleteProductResponse deleteProduct(@RequestPayload DeleteProductRequest request) {
        DeleteProductResponse response = new DeleteProductResponse();
        springJpaConfig.deleteProduct().process(ProductMapper.asProductEntity(request.getProduct()));
        response.setMessage("Product has been deleted with success");
        return response;
    }
}
