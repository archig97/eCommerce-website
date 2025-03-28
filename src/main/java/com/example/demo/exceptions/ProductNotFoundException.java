package com.example.demo.exceptions;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);//what does the super do in this case?
    }
}
