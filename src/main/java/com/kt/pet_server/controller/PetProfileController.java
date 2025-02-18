package com.kt.pet_server.controller;

import com.kt.pet_server.dto.response.pet.PetProfileDetailResponse;
import com.kt.pet_server.dto.response.pet.PetProfileListResponse;
import com.kt.pet_server.dto.response.pet.PetProfileSummaryResponse;
import com.kt.pet_server.global.base.BaseEntity;
import com.kt.pet_server.global.base.BaseResponse;
import com.kt.pet_server.service.PetProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "펫 프로필", description = "펫 프로필 관련 API")
@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetProfileController {

    private final PetProfileService petProfileService;

    @Operation(summary = "내 펫 프로필 전체 조회")
    @GetMapping("/my")
    public BaseResponse<PetProfileListResponse<PetProfileSummaryResponse>> getMyPetProfiles(
        HttpSession session
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");

        return BaseResponse.onSuccess(
            "내 펫 프로필 전체 조회 성공", petProfileService.getMyPetProfiles(sessionMemberId)
        );
    }

    @Operation(summary = "내 펫 프로필 상세 조회")
    @GetMapping("/my/{petId}")
    public BaseResponse<PetProfileDetailResponse> getMyPetProfileDetail(
        HttpSession session,
        @PathVariable Long petId
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");

        return BaseResponse.onSuccess(
            "내 펫 프로필 상세 조회 성공", petProfileService.getMyPetProfileDetail(sessionMemberId, petId)
        );
    }
}
