package com.kt.pet_server.repository.service;

import com.kt.pet_server.model.service.ServiceTypeMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTypeRepository extends JpaRepository<ServiceTypeMapping, Long> {
}
