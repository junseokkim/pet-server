package com.kt.pet_server.repository.petsitter;

import com.kt.pet_server.global.exception.CustomException;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kt.pet_server.model.member.Member;
import com.kt.pet_server.model.petsitter.PetSitterProfile;

public interface PetSitterProfileRepository extends JpaRepository<PetSitterProfile, Long> {

    default PetSitterProfile getPetSitterProfile(Long id) {
        return findById(id)
            .orElseThrow(() -> new CustomException("존재하지 않는 펫시터 프로필입니다."));
    }


    default PetSitterProfile getPetSitterProfileByMember(Member member) {
        return findByMember(member)
            .orElseThrow(() -> new CustomException("해당 멤버가 보유한 펫시터 프로필이 존재하지 않습니다."));
    }

    Optional<PetSitterProfile> findByMember(Member member);
}