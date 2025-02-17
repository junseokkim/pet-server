package com.kt.pet_server.service.impl;

import com.kt.pet_server.service.CodeDetailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.pet_server.dto.request.member.MemberSignupRequest;
import com.kt.pet_server.dto.request.member.MemberUpdatePasswordRequest;
import com.kt.pet_server.dto.request.member.MemberUpdateRequest;
import com.kt.pet_server.dto.response.member.MemberIdResponse;
import com.kt.pet_server.dto.response.member.MemberInquiryResponse;
import com.kt.pet_server.dto.response.member.MemberSignupResponse;
import com.kt.pet_server.global.exception.CustomException;
import com.kt.pet_server.model.member.Member;
import com.kt.pet_server.repository.MemberRepository;
import com.kt.pet_server.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CodeDetailService codeDetailService;

    @Override
    public MemberSignupResponse signup(final MemberSignupRequest request) {
        
        if (memberRepository.existsByEmail(request.email())) {
            throw new CustomException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        Member member = memberRepository.save(
            request.toEntity(encodedPassword, codeDetailService.getCodeDetailByName("일반 사용자"))
        );

        return MemberSignupResponse.from(member.getId());
    }

    @Override
    @Transactional
    public MemberIdResponse updateMyInfo(final Long memberId, final MemberUpdateRequest request) {  
        
        Member member = checkSessionMemberId(memberId);
        member.update(request);

        return MemberIdResponse.from(member.getId());
    }

    @Override
    @Transactional
    public MemberIdResponse withdraw(final Long memberId) {

        Member member = checkSessionMemberId(memberId);
        memberRepository.delete(member);

        return MemberIdResponse.from(member.getId());
    }

    @Override
    public MemberInquiryResponse inquiryMyInfo(final Long memberId) {

        Member member = checkSessionMemberId(memberId);
        return MemberInquiryResponse.from(member);
    }

    @Override
    @Transactional
    public MemberIdResponse updatePassword(Long memberId, MemberUpdatePasswordRequest request) {
        Member member = checkSessionMemberId(memberId);
        if (!passwordEncoder.matches(request.currentPassword(), member.getPassword())) {
            throw new CustomException("현재 비밀번호가 일치하지 않습니다.");
        }

        String password = request.newPassword();
        member.updatePassword(passwordEncoder.encode(password));
        return MemberIdResponse.from(member.getId());
    }

    private Member checkSessionMemberId(Long sessionMemberId) {
        if (sessionMemberId == null) {
            throw new CustomException("로그인이 필요합니다.");
        }

        return memberRepository.getMember(sessionMemberId);
    }


    
    
}
