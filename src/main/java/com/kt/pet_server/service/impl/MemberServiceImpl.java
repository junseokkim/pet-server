package com.kt.pet_server.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kt.pet_server.dto.request.member.MemberSignupRequest;
import com.kt.pet_server.dto.response.member.MemberSignupResponse;
import com.kt.pet_server.model.Member;
import com.kt.pet_server.repository.MemberRepository;
import com.kt.pet_server.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberSignupResponse signup(final MemberSignupRequest request) {
        
        if (memberRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        Member member = memberRepository.save(request.toEntity(encodedPassword));

        return MemberSignupResponse.from(member.getId());
    }
    
    
}
