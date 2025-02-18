package com.kt.pet_server.repository.service;

import com.kt.pet_server.global.exception.CustomException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.pet_server.model.petsitter.PetSitterProfile;
import com.kt.pet_server.model.service.PetSitterService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PetSitterServiceRepository extends JpaRepository<PetSitterService, Long> {

    default PetSitterService getPetSitterService(Long serviceId) {
        return findById(serviceId)
            .orElseThrow(() -> new CustomException("해당 펫시터 서비스를 찾을 수 없습니다."));
    }

    List<PetSitterService> findByPetSitter(PetSitterProfile petSitter);

    /**
     * TimeSlot 기준으로 해당 기간에 해당하는 서비스 조회
     */
    @Query("SELECT DISTINCT s FROM PetSitterService s " +
        "JOIN s.timeSlots ts " +
        "WHERE ts.date BETWEEN :startDate AND :endDate")
    List<PetSitterService> findByTimeSlotsBetween(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
} 