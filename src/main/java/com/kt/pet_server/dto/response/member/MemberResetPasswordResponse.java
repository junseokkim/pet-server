package com.kt.pet_server.dto.response.member;

public record  MemberResetPasswordResponse(
    String temporaryPassword
) {
    public static MemberResetPasswordResponse from(String temporaryPassword) {
        return new MemberResetPasswordResponse(temporaryPassword);
    }
}
