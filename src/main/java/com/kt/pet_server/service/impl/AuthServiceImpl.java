package com.kt.pet_server.service.impl;

import org.springframework.stereotype.Service;

import com.kt.pet_server.dto.request.auth.AuthLoginRequest;
import com.kt.pet_server.dto.response.auth.AuthLoginResponse;
import com.kt.pet_server.repository.MemberRepository;
import com.kt.pet_server.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;

    @Override
    public AuthLoginResponse login(final AuthLoginRequest request) {


        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    

}
