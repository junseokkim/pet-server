package com.kt.pet_server.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record AuthLoginRequest(
    @Email(message="이메일 형식이 아닙니다.")
    String email,
    @NotBlank(message="비밀번호는 필수 입력값입니다.")
    String password
) {
    
}
