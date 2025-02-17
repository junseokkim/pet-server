package com.kt.pet_server.dto.response.code_group;

import java.util.List;

public record CodeGroupListResponse<T>(
    List<T> codeGroupList
) {

    public static <T> CodeGroupListResponse<T> from(List<T> codeGroupList) {
        return new CodeGroupListResponse<>(codeGroupList);
    }
}
