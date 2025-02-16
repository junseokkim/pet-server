package com.kt.pet_server.dto.response.petsitter;

import java.util.List;

public record PetSitterListResponse<T> (
    List<T> petSitters
) {
    public static <T> PetSitterListResponse<T> from(List<T> petSitters) {
        return new PetSitterListResponse<>(petSitters);
    }
}
