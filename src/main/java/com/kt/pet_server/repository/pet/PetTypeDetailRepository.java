package com.kt.pet_server.repository.pet;

import com.kt.pet_server.global.exception.CustomException;
import com.kt.pet_server.model.pet.PetTypeDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetTypeDetailRepository extends JpaRepository<PetTypeDetail, Long> {
    default PetTypeDetail getPetTypeDetail(Long petTypeDetailId) {
        return findById(petTypeDetailId)
            .orElseThrow(() -> new CustomException("해당 펫 타입 디테일이 존재하지 않습니다."));
    }
}
