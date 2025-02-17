package com.kt.pet_server.dto.response.code_detail;

import com.kt.pet_server.model.code.CodeDetail;

public record CodeDetailSummaryResponse(
    String codeDetailId,
    String codeDetailName
) {

    public static CodeDetailSummaryResponse from(CodeDetail codeDetail) {
        return new CodeDetailSummaryResponse(codeDetail.getCodeDetailId(), codeDetail.getCodeDetailName());
    }
}
