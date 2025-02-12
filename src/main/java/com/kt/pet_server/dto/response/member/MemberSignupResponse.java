package com.kt.pet_server.dto.response.member;

public record MemberSignupResponse(
    Long id
) {
    
    public static MemberSignupResponse from(Long id) {
        return new MemberSignupResponse(id);
    }
}
