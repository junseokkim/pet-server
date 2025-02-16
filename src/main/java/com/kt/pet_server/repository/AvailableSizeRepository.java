package com.kt.pet_server.repository;

import com.kt.pet_server.model.service.AvailableSizeMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailableSizeRepository extends JpaRepository<AvailableSizeMapping, Long> {
}
