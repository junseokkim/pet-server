package com.kt.pet_server.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kt.pet_server.dto.request.auth.AuthLoginRequest;
import com.kt.pet_server.dto.response.auth.AuthLoginResponse;
import com.kt.pet_server.global.exception.CustomException;
import com.kt.pet_server.model.Member;
import com.kt.pet_server.repository.MemberRepository;
import com.kt.pet_server.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthLoginResponse login(final AuthLoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
            .orElseThrow(() -> new CustomException("이메일 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw new CustomException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        return AuthLoginResponse.from(member.getId());
    }
}
