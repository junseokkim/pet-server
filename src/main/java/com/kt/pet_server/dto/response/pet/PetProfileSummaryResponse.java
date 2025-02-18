package com.kt.pet_server.dto.response.pet;

import com.kt.pet_server.model.pet.PetProfile;

public record PetProfileSummaryResponse(
    Long id,
    String name,
    String birth,
    String profileUrl,
    String petType
) {
    public static PetProfileSummaryResponse from(PetProfile pet) {
        return new PetProfileSummaryResponse(
            pet.getId(),
            pet.getName(),
            pet.getBirth().toString(),
            pet.getProfileUrl(),
            pet.getPetTypeDetail().getPetType().name()
        );
    }
}
