package com.kt.pet_server.dto.response.service;

import com.kt.pet_server.dto.response.petsitter.PetSitterSummaryResponse;
import com.kt.pet_server.model.enums.PetSize;
import com.kt.pet_server.model.enums.ServiceType;
import com.kt.pet_server.model.service.PetSitterService;

import java.util.List;

public record ServiceDetailResponse(
    Long serviceId,
    PetSitterSummaryResponse petSitter,
    List<ServiceType> serviceTypes,
    List<PetSize> availableSizes,
    List<AvailableTimeResponse> availableTimes,
    int hourlyPrice,
    Boolean isMine
) {

    public static ServiceDetailResponse from(
        PetSitterService petSitterService,
        List<ServiceType> serviceTypes,
        List<PetSize> availableSizes,
        List<AvailableTimeResponse> availableTimes,
        Boolean isMine
    ) {
        return new ServiceDetailResponse(
            petSitterService.getId(),
            PetSitterSummaryResponse.from(petSitterService.getPetSitter()),
            serviceTypes,
            availableSizes,
            availableTimes,
            petSitterService.getHourlyPrice(),
            isMine
        );
    }
}
