package com.kt.pet_server.dto.response.auth;

public record AuthLoginResponse(
    Long memberId
) {

    public static AuthLoginResponse from(Long memberId) {
        return new AuthLoginResponse(memberId);
    }
}
