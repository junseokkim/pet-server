package com.kt.pet_server.repository;

import com.kt.pet_server.model.petsitter.PetSitterProfile;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.pet_server.model.petsitter.AvailablePetType;
import com.kt.pet_server.model.service.PetSitterService;

public interface AvailablePetTypeRepository extends JpaRepository<AvailablePetType, Long> {
    List<AvailablePetType> findAllByPetSitter(PetSitterProfile petSitterProfile);
}
