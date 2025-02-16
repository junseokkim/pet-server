package com.kt.pet_server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kt.pet_server.global.exception.CustomException;
import com.kt.pet_server.model.member.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member getMember(Long memberId) {
        return findById(memberId)
            .orElseThrow(() -> new CustomException("존재하지 않는 회원 id입니다."));
    }
    Boolean existsByEmail(String email);
    Optional<Member> findByEmail(String email);
}
