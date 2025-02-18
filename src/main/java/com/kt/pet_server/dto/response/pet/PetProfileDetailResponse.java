package com.kt.pet_server.dto.response.pet;

import com.kt.pet_server.model.pet.PetProfile;

public record PetProfileDetailResponse(
    Long id,
    String name,
    String birth,
    String profileUrl,
    String petType,
    String petBreed
) {
    public static PetProfileDetailResponse from(PetProfile pet) {
        return new PetProfileDetailResponse(
            pet.getId(),
            pet.getName(),
            pet.getBirth().toString(),
            pet.getProfileUrl(),
            pet.getPetTypeDetail().getPetType().getDescription(),
            pet.getPetTypeDetail().getName()
        );
    }
}
