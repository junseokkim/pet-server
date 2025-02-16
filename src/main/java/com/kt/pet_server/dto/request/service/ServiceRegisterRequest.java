package com.kt.pet_server.dto.request.service;

import com.kt.pet_server.model.enums.PetSize;
import com.kt.pet_server.model.enums.ServiceType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ServiceRegisterRequest(
    List<ServiceType> serviceTypes,
    List<PetSize> availableSizes,
    List<AvailableTimeRequest> availableTimes,
    int hourlyPrice,
    LocalDate availableStartDate,
    LocalDate availableEndDate
) {}