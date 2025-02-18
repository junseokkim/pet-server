package com.kt.pet_server.service;

import com.kt.pet_server.dto.response.pet.PetProfileDetailResponse;
import com.kt.pet_server.dto.response.pet.PetProfileListResponse;
import com.kt.pet_server.dto.response.pet.PetProfileSummaryResponse;

import java.util.List;

public interface PetProfileService {

    PetProfileListResponse<PetProfileSummaryResponse> getMyPetProfiles(Long sessionMemberId);
    PetProfileDetailResponse getMyPetProfileDetail(Long sessionMemberId, Long petId);
}
