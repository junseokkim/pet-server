package com.kt.pet_server.service.impl;

import com.kt.pet_server.repository.code.CodeDetailRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.pet_server.dto.request.auth.AuthLoginRequest;
import com.kt.pet_server.dto.response.auth.AuthLoginResponse;
import com.kt.pet_server.dto.response.auth.AuthResetPasswordResponse;
import com.kt.pet_server.global.exception.CustomException;
import com.kt.pet_server.model.member.Member;
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

        Boolean isAdmin = member.getRole().getCodeDetailName().equals("관리자");

        return AuthLoginResponse.from(member, isAdmin);
    }

    @Override
    @Transactional
    public AuthResetPasswordResponse resetPassword(String email) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException("존재하지 않는 이메일입니다."));
       
        member.updatePassword(passwordEncoder.encode("1234"));

        return AuthResetPasswordResponse.from("1234");
        
    }

    @Override
    public Member getMember(Long memberId) {
        if (memberId == null) {
            throw new CustomException("로그인을 해주세요");
        }

        return memberRepository.getMember(memberId);
    }

    @Override
    public void checkAdmin(Long memberId) {

        Member member = getMember(memberId);

        if (!member.getRole().getCodeDetailName().equals("관리자")) {
            throw new CustomException("관리자 권한이 필요합니다.");
        }
    }
}
