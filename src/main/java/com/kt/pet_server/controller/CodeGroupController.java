package com.kt.pet_server.controller;

import com.kt.pet_server.dto.request.code_group.CodeGroupUpdateRequest;
import com.kt.pet_server.dto.response.code_group.CodeGroupIdResponse;
import com.kt.pet_server.dto.response.code_group.CodeGroupInquiryResponse;
import com.kt.pet_server.dto.response.code_group.CodeGroupListResponse;
import com.kt.pet_server.global.base.BaseResponse;
import com.kt.pet_server.service.CodeGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    private final CodeGroupService codeGroupService;

    @Operation(summary = "코드 그룹 등록")
    @PostMapping
    public BaseResponse<CodeGroupIdResponse> createCodeGroup(
        HttpSession session,
        @RequestBody @Valid CodeGroupUpdateRequest request
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");

        return BaseResponse.onSuccess(
            "코드 그룹 등록 성공", codeGroupService.createCodeGroup(sessionMemberId, request)
        );
    }

    @Operation(summary = "코드 그룹 수정")
    @PatchMapping
    public BaseResponse<CodeGroupIdResponse> updateCodeGroup(
        HttpSession session,
        @RequestBody @Valid CodeGroupUpdateRequest request
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");

        return BaseResponse.onSuccess(
            "코드 그룹 수정 성공", codeGroupService.updateCodeGroup(sessionMemberId, request)
        );
    }

    @Operation(summary = "코드 그룹 삭제")
    @DeleteMapping("/{groupId}")
    public BaseResponse<CodeGroupIdResponse> deleteCodeGroup(
        HttpSession session,
        @PathVariable String groupId
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");

        return BaseResponse.onSuccess(
            "코드 그룹 삭제 성공", codeGroupService.deleteCodeGroup(sessionMemberId, groupId)
        );
    }

    @Operation(summary = "코드 그룹 목록 조회")
    @GetMapping
    public BaseResponse<CodeGroupListResponse<CodeGroupInquiryResponse>> getCodeGroupList(
        HttpSession session
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");

        return BaseResponse.onSuccess(
            "코드 그룹 목록 조회 성공", codeGroupService.getCodeGroupList(sessionMemberId)
        );
    }

}
