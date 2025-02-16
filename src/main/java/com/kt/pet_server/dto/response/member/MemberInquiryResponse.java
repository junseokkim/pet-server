package com.kt.pet_server.dto.response.member;

import com.kt.pet_server.model.member.Member;

public record  MemberInquiryResponse(
    Long memberId,
    String email,
    String name,
    String contact
) {

    public static MemberInquiryResponse from(Member member) {
        return new MemberInquiryResponse(
            member.getId(),
            member.getEmail(),
            member.getName(),
            member.getContact()
        );
    }
}
