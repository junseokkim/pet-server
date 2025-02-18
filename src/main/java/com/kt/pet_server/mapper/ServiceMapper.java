package com.kt.pet_server.mapper;

import com.kt.pet_server.dto.request.service.ServiceRegisterRequest;
import com.kt.pet_server.model.enums.PetSize;
import com.kt.pet_server.model.enums.ServiceType;
import com.kt.pet_server.model.petsitter.PetSitterProfile;
import com.kt.pet_server.model.service.AvailableSizeMapping;
import com.kt.pet_server.model.service.PetSitterService;
import com.kt.pet_server.model.service.ServiceTypeMapping;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {

    public PetSitterService toPetSitterService(
        PetSitterProfile petSitterProfile, ServiceRegisterRequest request
    ) {
        return PetSitterService.builder()
            .petSitter(petSitterProfile)
            .hourlyPrice(request.hourlyPrice())
            .availableStartDate(request.availableStartDate())
            .availableEndDate(request.availableEndDate())
            .status(false)
            .build();
    }

    public AvailableSizeMapping toAvailableSizeMapping(PetSize petSize, PetSitterService petSitterService) {
        return AvailableSizeMapping.builder()
            .size(petSize)
            .petSitterService(petSitterService)
            .build();
    }

    public ServiceTypeMapping toServiceTypeMapping(ServiceType serviceType, PetSitterService petSitterService) {
        return ServiceTypeMapping.builder()
            .type(serviceType)
            .petSitterService(petSitterService)
            .build();
    }
}