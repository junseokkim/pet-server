package com.kt.pet_server.repository.petsitter;

import com.kt.pet_server.model.petsitter.PetSitterProfile;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.pet_server.model.petsitter.AvailablePetType;

public interface AvailablePetTypeRepository extends JpaRepository<AvailablePetType, Long> {
    List<AvailablePetType> findAllByPetSitter(PetSitterProfile petSitterProfile);
}
