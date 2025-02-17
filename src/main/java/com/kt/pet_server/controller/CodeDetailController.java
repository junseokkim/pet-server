package com.kt.pet_server.controller;

import com.kt.pet_server.dto.request.code_detail.CodeDetailCreateRequest;
import com.kt.pet_server.dto.request.code_detail.CodeDetailUpdateRequest;
import com.kt.pet_server.dto.response.code_detail.CodeDetailIdResponse;
import com.kt.pet_server.dto.response.code_detail.CodeDetailListResponse;
import com.kt.pet_server.dto.response.code_detail.CodeDetailResponse;
import com.kt.pet_server.dto.response.code_detail.CodeDetailSummaryResponse;
import com.kt.pet_server.global.base.BaseResponse;
import com.kt.pet_server.service.CodeDetailService;
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

@Tag(name = "코드 상세 관리", description = "코드 상세 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/code-details")
public class CodeDetailController {

    private final CodeDetailService codeDetailService;

    @Operation(summary = "상세 코드 등록")
    @PostMapping
    public BaseResponse<CodeDetailIdResponse> createCodeDetail(
        HttpSession session,
        @RequestBody @Valid CodeDetailCreateRequest request
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");

        return BaseResponse.onSuccess(
            "상세 코드 등록 성공", codeDetailService.createCodeDetail(sessionMemberId, request)
        );
    }

    @Operation(summary = "상세 코드 수정")
    @PatchMapping()
    public BaseResponse<CodeDetailIdResponse> updateCodeDetail(
        HttpSession session,
        @RequestBody @Valid CodeDetailUpdateRequest request
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");


        return BaseResponse.onSuccess(
            "상세 코드 수정 성공", codeDetailService.updateCodeDetail(sessionMemberId, request)
        );
    }

    @Operation(summary = "상세 코드 삭제")
    @DeleteMapping("/{codeDetailId}")
    public BaseResponse<CodeDetailIdResponse> deleteCodeDetail(
        HttpSession session,
        @PathVariable String codeDetailId
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");

        return BaseResponse.onSuccess(
            "상세 코드 삭제 성공", codeDetailService.deleteCodeDetail(sessionMemberId, codeDetailId)
        );
    }

    @Operation(summary = "상세 코드 상세 조회")
    @GetMapping("/{codeDetailId}")
    public BaseResponse<CodeDetailResponse> getCodeDetail(
        HttpSession session,
        @PathVariable String codeDetailId
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");

        return BaseResponse.onSuccess(
            "상세 코드 조회 성공", codeDetailService.getCodeDetail(sessionMemberId, codeDetailId)
        );
    }

    @Operation(summary = "특정 코드 그룹의 상세 코드 목록 조회")
    @GetMapping("/group/{codeGroupId}")
    public BaseResponse<CodeDetailListResponse<CodeDetailResponse>> getCodeDetailList(
        HttpSession session,
        @PathVariable String codeGroupId
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");

        return BaseResponse.onSuccess(
            "상세 코드 목록 조회 성공", codeDetailService.getCodeDetailsByGroup(sessionMemberId, codeGroupId)
        );
    }
}
