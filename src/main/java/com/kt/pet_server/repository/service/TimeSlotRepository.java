package com.kt.pet_server.repository.service;

import com.kt.pet_server.model.service.TimeSlot;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    Optional<TimeSlot> findByIdAndIsBookedFalse(Long id);
}
