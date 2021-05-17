package com.example.usescase.exception;

public class MerchantNotFoundException extends RuntimeException {
    public MerchantNotFoundException(Integer id) {
        super("The merchant with id : "+id+" not found");
    }
}
