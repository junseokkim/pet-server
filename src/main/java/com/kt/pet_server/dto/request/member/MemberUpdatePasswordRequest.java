package com.kt.pet_server.dto.request.member;

public record MemberUpdatePasswordRequest(
    String currentPassword,
    String newPassword
) {
    
}
