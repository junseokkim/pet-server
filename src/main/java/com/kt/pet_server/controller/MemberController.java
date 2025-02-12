package com.kt.pet_server.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kt.pet_server.dto.request.member.MemberSignupRequest;
import com.kt.pet_server.dto.request.member.MemberUpdateRequest;
import com.kt.pet_server.dto.response.member.MemberIdResponse;
import com.kt.pet_server.dto.response.member.MemberInquiryResponse;
import com.kt.pet_server.dto.response.member.MemberResetPasswordResponse;
import com.kt.pet_server.dto.response.member.MemberSignupResponse;
import com.kt.pet_server.global.base.BaseResponse;
import com.kt.pet_server.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@Tag(name = "회원 API", description = "회원 API 명세서")
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "회원가입을 합니다.")
    @PostMapping
    public BaseResponse<MemberSignupResponse> signup(
        @RequestBody @Valid MemberSignupRequest request
    ) { 
        return BaseResponse.onSuccess(
            "가입 성공", memberService.signup(request)
        );
    }

    @Operation(summary = "회원 수정", description = "회원 정보 수정합니다.")
    @PatchMapping
    public BaseResponse<MemberIdResponse> updateMyInfo(
        @RequestBody @Valid MemberUpdateRequest request,
        HttpSession session
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");
        
        return BaseResponse.onSuccess(
            "수정 성공", memberService.updateMyInfo(sessionMemberId, request)
        );
    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴합니다.")
    @DeleteMapping
    public BaseResponse<MemberIdResponse> withdraw(
        HttpSession session
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");
    
        return BaseResponse.onSuccess(
            "탈퇴 성공", memberService.withdraw(sessionMemberId)
        );
    }

    @Operation(summary = "회원 조회", description = "회원 정보를 조회합니다.")
    @GetMapping
    public BaseResponse<MemberInquiryResponse> inquiryMyInfo(
        HttpSession session
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");
        
        return BaseResponse.onSuccess(
            "조회 성공", memberService.inquiryMyInfo(sessionMemberId)
        );
    }

    @Operation(summary = "회원 비밀번호 초기화", description = "회원 비밀번호를 초기화합니다.")
    @PutMapping("/reset")
    public BaseResponse<MemberResetPasswordResponse> resetPassword(
        @RequestParam String email
    ) {
        
        return BaseResponse.onSuccess(
            "비밀번호 초기화 성공", memberService.resetPassword(email)
        );
    }
    
}
