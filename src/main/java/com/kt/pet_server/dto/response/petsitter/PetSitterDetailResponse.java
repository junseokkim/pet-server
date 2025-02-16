package com.kt.pet_server.dto.response.petsitter;

import com.kt.pet_server.model.address.Address;
import com.kt.pet_server.model.petsitter.PetSitterProfile;
import java.util.List;

public record PetSitterDetailResponse(
    Long petSitterId,
    String name,
    String contact,
    int price,
    String availableTime,
    String introduce,
    String experience,
    String profileUrl,
    String location,
    List<String> petTypes,
    Boolean isMine
) {
    public static PetSitterDetailResponse from(
        PetSitterProfile petSitterProfile, List<String> petTypes, Boolean isMine
    ) {
        return new PetSitterDetailResponse(
            petSitterProfile.getId(),
            petSitterProfile.getName(),
            petSitterProfile.getContact(),
            petSitterProfile.getPrice(),
            petSitterProfile.getAvailableTime(),
            petSitterProfile.getIntroduce(),
            petSitterProfile.getExperience(),
            petSitterProfile.getProfileUrl(),
            getFullAddress(petSitterProfile.getAddress()),
            petTypes,
            isMine
        );
    }

    private static String getFullAddress(Address address){
        return address.getRegion() + " " + address.getDistrict() + " " + address.getNeighborhood();
    }
}
