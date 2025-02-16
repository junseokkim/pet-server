package com.kt.pet_server.dto.response.service;

import java.util.List;

public record ServiceListResponse<T>(
    List<T> services
) {

    public static <T> ServiceListResponse<T> from(List<T> services) {
        return new ServiceListResponse<>(services);
    }
}
