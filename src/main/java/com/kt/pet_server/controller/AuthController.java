package com.kt.pet_server.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kt.pet_server.dto.request.auth.AuthLoginRequest;
import com.kt.pet_server.dto.response.auth.AuthLoginResponse;
import com.kt.pet_server.dto.response.auth.AuthResetPasswordResponse;
import com.kt.pet_server.global.base.BaseResponse;
import com.kt.pet_server.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@Tag(name = "인증 API", description = "인증 API 명세서")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Tag(name = "로그인", description = "로그인 API")
    @PostMapping
    public BaseResponse<AuthLoginResponse> login(
        @RequestBody AuthLoginRequest request,
        HttpSession session
    ) {
        AuthLoginResponse response = authService.login(request);
        session.setAttribute("memberId", response.memberId());
        
        return BaseResponse.onSuccess("로그인 성공", response);
    }

    @Operation(summary = "회원 비밀번호 초기화", description = "회원 비밀번호를 초기화합니다.")
    @PutMapping("/reset")
    public BaseResponse<AuthResetPasswordResponse> resetPassword(
        @RequestParam String email
    ) {
        return BaseResponse.onSuccess(
            "비밀번호 초기화 성공", authService.resetPassword(email)
        );
    }
}
