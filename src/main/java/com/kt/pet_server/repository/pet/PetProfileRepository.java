package com.kt.pet_server.repository.pet;

import com.kt.pet_server.model.member.Member;
import com.kt.pet_server.model.pet.PetProfile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetProfileRepository extends JpaRepository<PetProfile, Long> {
    List<PetProfile> findByMember(Member member);
    Optional<PetProfile> findByIdAndMember(Long petId, Member member);
}
