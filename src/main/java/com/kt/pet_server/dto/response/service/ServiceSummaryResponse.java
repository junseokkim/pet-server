package com.kt.pet_server.dto.response.service;

import java.time.LocalDate;
import java.util.List;

import com.kt.pet_server.dto.response.petsitter.PetSitterSummaryResponse;
import com.kt.pet_server.model.enums.PetSize;
import com.kt.pet_server.model.enums.ServiceType;
import com.kt.pet_server.model.service.PetSitterService;

public record ServiceSummaryResponse(
    Long serviceId,
    PetSitterSummaryResponse petSitter,
    List<ServiceType> serviceTypes,
    List<PetSize> availableSizes,
    LocalDate availableStartDate,
    LocalDate availableEndDate,
    int hourlyPrice
) {

    public static ServiceSummaryResponse from(
        PetSitterService petSitterService, 
        List<ServiceType> serviceTypes,
        List<PetSize> availableSizes,
        List<AvailableTimeResponse> availableTimes
    ) {
        return new ServiceSummaryResponse(
            petSitterService.getId(),
            PetSitterSummaryResponse.from(petSitterService.getPetSitter()),
            serviceTypes,
            availableSizes,
            petSitterService.getAvailableStartDate(),
            petSitterService.getAvailableEndDate(),
            petSitterService.getHourlyPrice()
        );
    }
}
