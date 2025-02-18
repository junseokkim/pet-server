package com.kt.pet_server.dto.response.service;

import com.kt.pet_server.dto.response.petsitter.PetSitterSummaryResponse;
import com.kt.pet_server.model.enums.PetSize;
import com.kt.pet_server.model.enums.ServiceType;
import com.kt.pet_server.model.service.PetSitterService;

import java.time.LocalDate;
import java.util.List;

public record ServiceSummaryResponse(
    Long serviceId,
    PetSitterSummaryResponse petSitter,
    List<String> serviceTypes,
    List<String> availableSizes,
    List<AvailableTimeResponse> availableTimes,
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
            serviceTypes.stream().map(ServiceType::getDescription).toList(),
            availableSizes.stream().map(PetSize::getDescription).toList(),
            availableTimes,
            petSitterService.getAvailableStartDate(),
            petSitterService.getAvailableEndDate(),
            petSitterService.getHourlyPrice()
        );
    }
}
