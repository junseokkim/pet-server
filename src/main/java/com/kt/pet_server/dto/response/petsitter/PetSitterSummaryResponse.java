package com.kt.pet_server.dto.response.petsitter;

import com.kt.pet_server.model.petsitter.PetSitterProfile;

public record PetSitterSummaryResponse(
    Long petSitterId,
    String name,
    String profileImageUrl,
    String introduce
) {

    public static PetSitterSummaryResponse from(PetSitterProfile petSitter) {
        return new PetSitterSummaryResponse(
            petSitter.getId(), 
            petSitter.getName(), 
            petSitter.getProfileUrl(),
            petSitter.getIntroduce()
            );
    }

}
