package com.kt.pet_server.dto.request.member;

import com.kt.pet_server.model.Member;
import com.kt.pet_server.model.enums.MemberStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberSignupRequest(
    @Email(message="이메일 형식이 아닙니다.")
    String email,
    @NotBlank(message="비밀번호는 필수 입력값입니다.")
    String password,
    @NotBlank(message="이름은 필수 입력값입니다.")
    String name,
    @NotBlank(message="연락처는 필수 입력값입니다.")
    String contact
) {

    public Member toEntity(String encodedPassword) {
        return Member.builder()
            .email(email)
            .password(encodedPassword)
            .name(name)
            .contact(contact)
            .status(MemberStatus.ACTIVE)
            .build();
    }

}
