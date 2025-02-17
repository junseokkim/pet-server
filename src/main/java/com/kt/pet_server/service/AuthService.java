package com.kt.pet_server.service;

import com.kt.pet_server.dto.request.auth.AuthLoginRequest;
import com.kt.pet_server.dto.response.auth.AuthLoginResponse;
import com.kt.pet_server.dto.response.auth.AuthResetPasswordResponse;
import com.kt.pet_server.model.member.Member;

public interface AuthService {

    AuthLoginResponse login(final AuthLoginRequest request);
    AuthResetPasswordResponse resetPassword(final String email);
    Member getMember(final Long memberId);
    void checkAdmin(final Long memberId);
}
