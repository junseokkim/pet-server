package com.kt.pet_server.dto.response.code_detail;

public record CodeDetailIdResponse(
    String codeDetailId
) {
    public static CodeDetailIdResponse from(String codeDetailId) {
        return new CodeDetailIdResponse(codeDetailId);
    }
}
