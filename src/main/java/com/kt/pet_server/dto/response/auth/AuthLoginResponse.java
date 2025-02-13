package com.kt.pet_server.dto.response.auth;

import com.kt.pet_server.model.Member;

public record AuthLoginResponse(
    Long memberId,
    String name
) {

    public static AuthLoginResponse from(Member member) {
        return new AuthLoginResponse(
            member.getId(),
            member.getName()
        );
    }
}
