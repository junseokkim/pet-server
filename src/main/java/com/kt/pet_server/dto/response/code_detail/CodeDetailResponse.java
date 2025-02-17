package com.kt.pet_server.dto.response.code_detail;

import com.kt.pet_server.model.code.CodeDetail;

public record CodeDetailResponse(
    String codeDetailId,
    String codeDetailName,
    String codeDetailValue,
    Integer sortOrder,
    Boolean isActive
) {

    public static CodeDetailResponse from(CodeDetail codeDetail) {
        return new CodeDetailResponse(
            codeDetail.getCodeDetailId(),
            codeDetail.getCodeDetailName(),
            codeDetail.getCodeDetailValue(),
            codeDetail.getSortOrder(),
            codeDetail.getIsActive()
        );
    }
}
