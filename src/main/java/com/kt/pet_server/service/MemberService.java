package com.kt.pet_server.service;

import com.kt.pet_server.dto.request.member.MemberSignupRequest;
import com.kt.pet_server.dto.response.member.MemberSignupResponse;

public interface MemberService {
    MemberSignupResponse signup(final MemberSignupRequest request);
}
