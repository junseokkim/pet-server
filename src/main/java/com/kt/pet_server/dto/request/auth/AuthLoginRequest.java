package com.kt.pet_server.dto.request.auth;


public record AuthLoginRequest(
    String email,
    String password
) {
    
}
