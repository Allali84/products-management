package com.example.cxf.service;

import com.example.cxf.models.ProductCxf;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface ProductService {
    @WebMethod
    ProductCxf getProduct(ProductCxf product);
    @WebMethod
    ProductCxf createProduct(ProductCxf product);
    @WebMethod
    ProductCxf updateProduct(ProductCxf product);
    @WebMethod
    String deleteProduct(ProductCxf product);
}
