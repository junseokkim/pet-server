package com.kt.pet_server.dto.response.code_detail;

import java.util.List;

public record CodeDetailListResponse <T> (
    List<T> codeDetailList
) {

    public static <T> CodeDetailListResponse<T> fromEntity(List<T> codeDetailList) {
        return new CodeDetailListResponse<>(codeDetailList);
    }
}
