package com.kt.pet_server.dto.request.code_detail;

import com.kt.pet_server.model.code.CodeDetail;
import com.kt.pet_server.model.code.CodeGroup;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CodeDetailUpdateRequest(
    @NotBlank(message = "코드 상세 ID를 입력해주세요.")
    String codeDetailId,
    @NotBlank(message = "코드 그룹 ID를 입력해주세요.")
    String codeDetailName,
    @NotBlank(message = "코드 상세 값을 입력해주세요.")
    String codeDetailValue,
    @Min(value = 1, message = "순서는 1 이상의 값을 입력해주세요.")
    Integer sortOrder
) {

    public static CodeDetail toEntity(CodeDetailUpdateRequest request, CodeGroup codeGroup) {
        return CodeDetail.builder()
            .codeDetailId(request.codeDetailId())
            .codeGroup(codeGroup)
            .codeDetailName(request.codeDetailName())
            .codeDetailValue(request.codeDetailValue())
            .sortOrder(request.sortOrder())
            .isActive(Boolean.TRUE)
            .build();
    }
}
