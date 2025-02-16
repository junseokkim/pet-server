package com.kt.pet_server.repository;

import com.kt.pet_server.model.service.AvailableTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailableTimeRepository extends JpaRepository<AvailableTime, Long> {
}
