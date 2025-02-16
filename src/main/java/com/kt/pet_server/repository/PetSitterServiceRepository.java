package com.kt.pet_server.repository;

import com.kt.pet_server.global.exception.CustomException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.pet_server.model.petsitter.PetSitterProfile;
import com.kt.pet_server.model.service.PetSitterService;

public interface PetSitterServiceRepository extends JpaRepository<PetSitterService, Long> {

    default PetSitterService getPetSitterService(Long serviceId) {
        return findById(serviceId)
            .orElseThrow(() -> new CustomException("해당 펫시터 서비스를 찾을 수 없습니다."));
    }

    List<PetSitterService> findByPetSitter(PetSitterProfile petSitter);

    List<PetSitterService> findByAvailableStartDateBetweenOrderByAvailableStartDate(
        LocalDate startDate, LocalDate endDate
    );
} 