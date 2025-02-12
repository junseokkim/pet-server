package com.kt.pet_server.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResultErrorMessage implements ResultMessage {

    
    ;
    private final String message;   

}
