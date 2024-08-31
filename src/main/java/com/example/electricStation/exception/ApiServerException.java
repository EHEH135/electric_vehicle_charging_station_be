package com.example.electricStation.exception;

public class ApiServerException extends RuntimeException{
    public ApiServerException(String message) {
        super(message);
    }
}
