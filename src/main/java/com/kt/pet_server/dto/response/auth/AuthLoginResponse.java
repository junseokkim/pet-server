package com.kt.pet_server.dto.response.auth;

import com.kt.pet_server.model.member.Member;

public record AuthLoginResponse(
    Long memberId,
    String name,
    Boolean isAdmin
) {

    public static AuthLoginResponse from(Member member, Boolean isAdmin) {
        return new AuthLoginResponse(
            member.getId(),
            member.getName(),
            isAdmin
        );
    }
}
