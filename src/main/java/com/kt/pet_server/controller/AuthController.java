package com.kt.pet_server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kt.pet_server.service.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kt.pet_server.dto.request.auth.AuthLoginRequest;
import com.kt.pet_server.dto.response.auth.AuthLoginResponse;
import com.kt.pet_server.global.base.BaseEntity;

import jakarta.servlet.http.HttpSession;

@Tag(name = "인증 API", description = "인증 API 명세서")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @
    @PostMapping
    public BaseEntity<AuthLoginResponse> login(
        @RequestBody AuthLoginRequest request,
        HttpSession session
    ) {
        AuthLoginResponse response = authService.login(request);
        session.setAttribute("memberId", response.memberId());
        
        return BaseEntity.onSuccess("로그인 성공", response.memberId());
    }
    

}
