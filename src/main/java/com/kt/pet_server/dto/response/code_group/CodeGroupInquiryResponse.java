package com.kt.pet_server.dto.response.code_group;

import com.kt.pet_server.model.code.CodeGroup;

public record CodeGroupInquiryResponse(
    String codeGroupId,
    String codeGroupName,
    String codeGroupDescription
) {

    public static CodeGroupInquiryResponse from(CodeGroup codeGroup) {
        return new CodeGroupInquiryResponse(
            codeGroup.getGroupId(),
            codeGroup.getGroupName(),
            codeGroup.getDescription()
        );
    }
}
