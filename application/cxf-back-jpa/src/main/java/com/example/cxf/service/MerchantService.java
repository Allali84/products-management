package com.example.cxf.service;

import com.example.cxf.models.MerchantCxf;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface MerchantService {
    @WebMethod
    MerchantCxf getMerchant(MerchantCxf merchant);
    @WebMethod
    MerchantCxf createMerchant(MerchantCxf merchant);
    @WebMethod
    MerchantCxf updateMerchant(MerchantCxf merchant);
    @WebMethod
    String deleteMerchant(MerchantCxf merchant);
}
