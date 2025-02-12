package com.kt.pet_server.dto.response.auth;

public record  AuthResetPasswordResponse(
    String temporaryPassword
) {
    public static AuthResetPasswordResponse from(String temporaryPassword) {
        return new AuthResetPasswordResponse(temporaryPassword);
    }
}
