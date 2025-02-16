package com.kt.pet_server.dto.response.petsitter;

public record PetSitterIdResposne(
    Long petSitterId
) {
    public static PetSitterIdResposne from(Long petSitterId) {
        return new PetSitterIdResposne(petSitterId);
    }
}
