package com.kt.pet_server.dto.request.code_group;

import com.kt.pet_server.model.code.CodeGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CodeGroupUpdateRequest(
    @NotBlank(message = "코드 그룹을 입력해주세요.")
    String codeGroup,
    @NotBlank(message = "코드 그룹 이름을 입력해주세요.")
    String codeGroupName,
    @NotNull(message = "코드 그룹 설명을 입력해주세요.")
    String codeGroupDescription
) {

    public static CodeGroup toEntity(CodeGroupUpdateRequest request) {
        return CodeGroup.builder()
            .groupId(request.codeGroup())
            .groupName(request.codeGroupName())
            .description(request.codeGroupDescription())
            .build();
    }
}
