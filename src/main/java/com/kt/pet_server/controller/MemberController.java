package com.kt.pet_server.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.pet_server.dto.request.member.MemberSignupRequest;
import com.kt.pet_server.dto.response.member.MemberSignupResponse;
import com.kt.pet_server.global.base.BaseResponse;
import com.kt.pet_server.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public BaseResponse<MemberSignupResponse> postMethodName(@RequestBody @Valid MemberSignupRequest request) {
        
        return BaseResponse.onSuccess("가입성공", memberService.signup(request));
    }
}
