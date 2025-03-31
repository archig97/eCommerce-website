package com.example.demo.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
        //super invokes constructor of superclass RuntimeException
    }
}
