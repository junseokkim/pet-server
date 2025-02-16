package com.kt.pet_server.service.impl;

import com.kt.pet_server.dto.response.petsitter.PetSitterDetailResponse;
import com.kt.pet_server.dto.response.petsitter.PetSitterListResponse;
import com.kt.pet_server.mapper.PetSitterMapper;
import com.kt.pet_server.model.address.Address;
import com.kt.pet_server.model.enums.PetType;
import com.kt.pet_server.model.member.Member;
import com.kt.pet_server.model.petsitter.AvailablePetType;
import com.kt.pet_server.model.petsitter.PetSitterProfile;
import com.kt.pet_server.repository.AddressRepository;
import com.kt.pet_server.repository.AvailablePetTypeRepository;
import com.kt.pet_server.repository.CertificateRepository;
import com.kt.pet_server.repository.PetSitterProfileRepository;
import com.kt.pet_server.repository.PetSitterReviewRepository;
import com.kt.pet_server.repository.PetTypeDetailRepository;
import com.kt.pet_server.service.AuthService;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

import com.kt.pet_server.dto.request.petsitter.PetSitterRegisterRequest;
import com.kt.pet_server.dto.response.petsitter.PetSitterIdResposne;
import com.kt.pet_server.dto.response.petsitter.PetSitterSummaryResponse;
import com.kt.pet_server.service.PetSitterService;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PetSitterServiceImpl implements PetSitterService {

    private final AuthService authService;
    private final PetSitterMapper petSitterMapper;
    private final PetSitterProfileRepository petSitterRepository;
    private final AvailablePetTypeRepository availablePetTypeRepository;
    private final CertificateRepository certificateRepository;
    private final PetSitterReviewRepository petSitterReviewRepository;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public PetSitterIdResposne registerPetSitter(Long sessionMemberId, PetSitterRegisterRequest request) {
        Member member = authService.getMember(sessionMemberId);

        PetSitterProfile petSitterProfile = petSitterRepository.save(
            petSitterMapper.toPetSitterProfile(request, getAddress(request), member)
        );

        request.petTypes().forEach(type ->
            availablePetTypeRepository.save(petSitterMapper.toAvailablePetType(petSitterProfile, type))
        );

        return PetSitterIdResposne.from(petSitterProfile.getId());
    }

    @Override
    public PetSitterSummaryResponse getMyProfile(Long sessionMemberId) {
        Member member = authService.getMember(sessionMemberId);

        return PetSitterSummaryResponse.from(petSitterRepository.getPetSitterProfileByMember(member));
    }

    @Override
    public PetSitterListResponse<PetSitterSummaryResponse> getProfiles() {

        return PetSitterListResponse.from(
            petSitterRepository.findAll().stream()
                .map(PetSitterSummaryResponse::from)
                .toList()
        );
    }

    @Override
    public PetSitterDetailResponse getProfile(Long sessionMemberId, Long petSitterId) {
        Member member = authService.getMember(sessionMemberId);

        PetSitterProfile petSitterProfile = petSitterRepository.getPetSitterProfile(petSitterId);

        List<String> petTypes = availablePetTypeRepository.findAllByPetSitter(petSitterProfile).stream()
            .map(AvailablePetType::getPetType)
            .map(PetType::name)
            .toList();

        return PetSitterDetailResponse.from(
            petSitterProfile, petTypes, (Objects.equals(member.getId(), petSitterProfile.getMember().getId()))
        );
    }


    private Address getAddress(PetSitterRegisterRequest request) {
        return addressRepository.save(petSitterMapper.toAddress(request));
    }
}
