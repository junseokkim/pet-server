package com.kt.pet_server.service.impl;

import com.kt.pet_server.dto.response.pet.PetProfileDetailResponse;
import com.kt.pet_server.dto.response.pet.PetProfileListResponse;
import com.kt.pet_server.dto.response.pet.PetProfileSummaryResponse;
import com.kt.pet_server.global.exception.CustomException;
import com.kt.pet_server.model.member.Member;
import com.kt.pet_server.model.pet.PetProfile;
import com.kt.pet_server.repository.pet.PetProfileRepository;
import com.kt.pet_server.service.AuthService;
import com.kt.pet_server.service.PetProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetProfileServiceImpl implements PetProfileService {

    private final PetProfileRepository petProfileRepository;
    private final AuthService authService;

    @Override
    public PetProfileListResponse<PetProfileSummaryResponse> getMyPetProfiles(Long sessionMemberId) {
        Member member = authService.getMember(sessionMemberId);

        List<PetProfile> petProfiles = petProfileRepository.findByMember(member);
        return PetProfileListResponse.from(
            petProfiles.stream().map(PetProfileSummaryResponse::from).toList()
        );
    }

    @Override
    public PetProfileDetailResponse getMyPetProfileDetail(Long sessionMemberId, Long petId) {
        Member member = authService.getMember(sessionMemberId);

        PetProfile petProfile = petProfileRepository.findByIdAndMember(petId, member)
            .orElseThrow(() -> new CustomException("해당 펫 프로필을 찾을 수 없습니다."));
        return PetProfileDetailResponse.from(petProfile);
    }
}
