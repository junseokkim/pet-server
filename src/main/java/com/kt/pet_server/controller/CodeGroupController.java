package com.kt.pet_server.controller;

import com.kt.pet_server.dto.request.code_group.CodeGroupUpdateRequest;
import com.kt.pet_server.dto.response.code_group.CodeGroupIdResponse;
import com.kt.pet_server.dto.response.code_group.CodeGroupInquiryResponse;
import com.kt.pet_server.dto.response.code_group.CodeGroupListResponse;
import com.kt.pet_server.global.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "코드 그룹 관리", description = "코드 그룹 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/code-group")
public class CodeGroupController {

    @Operation(summary = "그룹 코드 등록")
    @PostMapping
    public BaseResponse<CodeGroupIdResponse> createCodeGroup(
        Session session,
        @RequestBody @Valid CodeGroupUpdateRequest request
        ) {
        return null;
    }

    @Operation(summary = "그룹 코드 수정")
    @PatchMapping("/{groupId}")
    public BaseResponse<CodeGroupIdResponse> updateCodeGroup(
        Session session,
        @PathVariable String groupId,
        @RequestBody @Valid CodeGroupUpdateRequest request
    ) {
        return null;
    }

    @Operation(summary = "그룹 코드 삭제")
    @DeleteMapping("/{groupId}")
    public BaseResponse<CodeGroupIdResponse> deleteCodeGroup(
        Session session,
        @PathVariable String groupId
    ) {
        return null;
    }

    @Operation(summary = "그룹 코드 목록 조회")
    @GetMapping
    public BaseResponse<CodeGroupListResponse<CodeGroupInquiryResponse>> getCodeGroupList(
        Session session
    ) {
        return null;
    }

}
