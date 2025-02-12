package com.kt.pet_server.dto.request.member;

import jakarta.validation.constraints.NotBlank;

public record MemberUpdateRequest(
    @NotBlank(message="이름은 필수 입력값입니다.")
    String name,
    @NotBlank(message="연락처는 필수 입력값입니다.")
    String contact
) {
}
