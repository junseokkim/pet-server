package com.kt.pet_server.dto.response.member;

public record MemberIdResponse(
    Long memberId
) {
    public static MemberIdResponse from(Long memberId) {
        return new MemberIdResponse(memberId);
    }
}
