package com.kt.pet_server.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private String message;

    public CustomException(String message) {
        super(message);
    }
}
