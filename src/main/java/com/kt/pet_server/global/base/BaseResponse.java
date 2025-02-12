package com.kt.pet_server.global.base;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseResponse<T> {

    private final String status;
        
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;

    public static <T> BaseResponse<T> onSuccess(String message, T data) {
        return new BaseResponse<>("success", message, data);
    }

    public static <T> BaseResponse<T> onFailure(String message) {
        return new BaseResponse<>("error", message, null);
    }
    
    
}
