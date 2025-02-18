package com.kt.pet_server.dto.response.pet;

import java.util.List;

public record PetProfileListResponse<T>(
    List<T> petProfiles
) {
    public static <T> PetProfileListResponse<T> from(List<T> petProfiles) {
        return new PetProfileListResponse<>(petProfiles);
    }
}
