package com.kt.pet_server.dto.response.service;

public record ServiceIdResponse(
    Long serviceId
) {
    public static ServiceIdResponse from(Long serviceId) {
        return new ServiceIdResponse(serviceId);
    }
}
