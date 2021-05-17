package com.example.usescase.exception;

public class MerchantNameNotFoundException extends RuntimeException {
    public MerchantNameNotFoundException(String name) {
        super("The merchant with name : "+name+" not found");
    }
}
