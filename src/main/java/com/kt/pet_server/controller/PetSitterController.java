package com.kt.pet_server.controller;

import com.kt.pet_server.dto.request.petsitter.PetSitterRegisterRequest;
import com.kt.pet_server.dto.request.service.ServiceRegisterRequest;

import com.kt.pet_server.dto.response.petsitter.PetSitterDetailResponse;
import com.kt.pet_server.dto.response.petsitter.PetSitterListResponse;
import com.kt.pet_server.dto.response.service.ServiceDetailResponse;
import com.kt.pet_server.service.PetSitterService;
import com.kt.pet_server.service.PetSitterServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import com.kt.pet_server.dto.response.petsitter.PetSitterIdResposne;
import com.kt.pet_server.dto.response.petsitter.PetSitterSummaryResponse;
import com.kt.pet_server.dto.response.service.ServiceIdResponse;
import com.kt.pet_server.dto.response.service.ServiceListResponse;
import com.kt.pet_server.dto.response.service.ServiceSummaryResponse;
import com.kt.pet_server.global.base.BaseResponse;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Tag(name = "펫시터 API", description = "펫시터 API 명세서")
@RestController
@RequestMapping("/pet-sitters")
@RequiredArgsConstructor
public class PetSitterController {

    private final PetSitterService petSitterService;
    private final PetSitterServiceService petSitterServiceService;

    // TODO: 프로필 이미지 및 인증서 파일 업로드 기능 추가
    @Operation(summary = "펫시터 프로필 등록")
    @PostMapping
    public BaseResponse<PetSitterIdResposne> registerPetSitter(
        @RequestBody @Valid PetSitterRegisterRequest request,
        HttpSession session
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");

        return BaseResponse.onSuccess(
            "등록 성공", petSitterService.registerPetSitter(sessionMemberId, request)
        );
    }

    @Operation(summary = "나의 펫시터 프로필 조회")
    @GetMapping
    public BaseResponse<PetSitterSummaryResponse> getMyProfile(
        HttpSession session
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");

        return BaseResponse.onSuccess(
            "조회 성공", petSitterService.getMyProfile(sessionMemberId)
        );
    }

    @Operation(summary = "펫시터 프로필 목록 조회")
    @GetMapping("/list")
    public BaseResponse<PetSitterListResponse<PetSitterSummaryResponse>> getProfiles() {
        return BaseResponse.onSuccess(
            "조회 성공", petSitterService.getProfiles()
        );
    }

    @Operation(summary = "펫시터 프로필 상세 조회")
    @GetMapping("/{petSitterId}")
    public BaseResponse<PetSitterDetailResponse> getProfile(
        @PathVariable Long petSitterId,
        HttpSession session
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");

        return BaseResponse.onSuccess(
            "조회 성공", petSitterService.getProfile(sessionMemberId, petSitterId)
        );
    }

    @Operation(summary = "펫시터 서비스 등록")
    @PostMapping("/services")
    public BaseResponse<ServiceIdResponse> registerService(
        @RequestBody ServiceRegisterRequest request,
        HttpSession session
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");

        return BaseResponse.onSuccess(
            "등록 성공", petSitterServiceService.registerService(sessionMemberId, request)
        );
    }

    @Operation(summary = "펫시터 서비스 등록 취소")
    @DeleteMapping("/services/{serviceId}")
    public BaseResponse<ServiceIdResponse> cancelService(
        @PathVariable Long serviceId,
        HttpSession session
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");

        return BaseResponse.onSuccess(
            "취소 성공", petSitterServiceService.cancelService(sessionMemberId, serviceId)
        );
    }

    @Operation(summary = "나의 펫시터 서비스 조회")
    @GetMapping("/services")
    public BaseResponse<ServiceListResponse<ServiceSummaryResponse>> getMyServices(
        HttpSession session
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");

        return BaseResponse.onSuccess(
            "조회 성공", petSitterServiceService.getMyServices(sessionMemberId)
        );
    }

    @Operation(summary = "특정 펫시터의 펫시터 서비스 리스트 조회")
    @GetMapping("/{petSitterId}/services")
    public BaseResponse<ServiceListResponse<ServiceSummaryResponse>> getServices(
        @PathVariable Long petSitterId
    ) {
        return BaseResponse.onSuccess(
            "조회 성공", petSitterServiceService.getServices(petSitterId)
        );
    }

    @Operation(summary = "월별 펫시터 서비스 조회")
    @GetMapping("/services/monthly")
    public BaseResponse<ServiceListResponse<ServiceSummaryResponse>> getMonthlyServices(
        @RequestParam String year,
        @RequestParam String month
    ) {
        return BaseResponse.onSuccess(
            "조회 성공", petSitterServiceService.getMonthlyServices(year, month)
        );
    }

    @Operation(summary = "펫시터 서비스 상세 조회")
    @GetMapping("/services/{serviceId}")
    public BaseResponse<ServiceDetailResponse> getService(
        @PathVariable Long serviceId
    ) {
        return BaseResponse.onSuccess(
            "조회 성공", petSitterServiceService.getService(serviceId)
        );
    }

}
