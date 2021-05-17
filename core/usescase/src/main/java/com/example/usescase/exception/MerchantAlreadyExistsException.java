package com.example.usescase.exception;

public class MerchantAlreadyExistsException extends RuntimeException {
    public MerchantAlreadyExistsException(Integer id) {
        super("The merchant with id : "+id+" already exist");
    }
}
