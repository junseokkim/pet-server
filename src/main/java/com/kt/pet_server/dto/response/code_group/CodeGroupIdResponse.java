package com.kt.pet_server.dto.response.code_group;

public record CodeGroupIdResponse(
    String codeGroupId
) {

    public static CodeGroupIdResponse from(String codeGroupId) {
        return new CodeGroupIdResponse(codeGroupId);
    }
}
