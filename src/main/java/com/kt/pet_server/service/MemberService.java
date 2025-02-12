package com.kt.pet_server.service;

import com.kt.pet_server.dto.request.member.MemberSignupRequest;
import com.kt.pet_server.dto.request.member.MemberUpdatePasswordRequest;
import com.kt.pet_server.dto.request.member.MemberUpdateRequest;
import com.kt.pet_server.dto.response.member.MemberIdResponse;
import com.kt.pet_server.dto.response.member.MemberInquiryResponse;
import com.kt.pet_server.dto.response.member.MemberSignupResponse;

public interface MemberService {
    MemberSignupResponse signup(final MemberSignupRequest request);
    MemberIdResponse updateMyInfo(final Long memberId, final MemberUpdateRequest request);
    MemberIdResponse withdraw(final Long memberId);
    MemberInquiryResponse inquiryMyInfo(final Long memberId);
    MemberIdResponse updatePassword(final Long memberId, final MemberUpdatePasswordRequest request);
}
