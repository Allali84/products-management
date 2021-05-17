package com.example.cxf.service;

import com.example.cxf.models.MerchantCxf;
import com.example.cxf.models.ProductCxf;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface MerchantProductService {
    @WebMethod
    MerchantCxf associateMerchantWithProduct(MerchantCxf merchant, ProductCxf product);
}
