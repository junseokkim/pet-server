package com.kt.pet_server.service;

import com.kt.pet_server.dto.request.petsitter.PetSitterRegisterRequest;
import com.kt.pet_server.dto.response.petsitter.PetSitterDetailResponse;
import com.kt.pet_server.dto.response.petsitter.PetSitterIdResposne;
import com.kt.pet_server.dto.response.petsitter.PetSitterListResponse;
import com.kt.pet_server.dto.response.petsitter.PetSitterSummaryResponse;
import com.kt.pet_server.model.petsitter.PetSitterProfile;

public interface PetSitterService {

    PetSitterIdResposne registerPetSitter(Long sessionMemberId, PetSitterRegisterRequest request);
    PetSitterSummaryResponse getMyProfile(Long sessionMemberId);
    PetSitterListResponse<PetSitterSummaryResponse> getProfiles();
    PetSitterDetailResponse getProfile(Long sessionMemberId, Long petSitterId);
    PetSitterProfile loadPetSitterProfile(Long petSitterId);
}
