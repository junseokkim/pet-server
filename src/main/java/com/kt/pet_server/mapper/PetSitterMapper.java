package com.kt.pet_server.mapper;

import com.kt.pet_server.dto.request.petsitter.PetSitterRegisterRequest;
import com.kt.pet_server.model.address.Address;
import com.kt.pet_server.model.enums.PetType;
import com.kt.pet_server.model.member.Member;
import com.kt.pet_server.model.pet.PetTypeDetail;
import com.kt.pet_server.model.petsitter.AvailablePetType;
import com.kt.pet_server.model.petsitter.Certificate;
import com.kt.pet_server.model.petsitter.PetSitterProfile;
import org.springframework.stereotype.Component;

@Component
public class PetSitterMapper {

    public PetSitterProfile toPetSitterProfile(
        PetSitterRegisterRequest request, Address address, Member member
    ) {
        return PetSitterProfile.builder()
            .name(request.name())
            .contact(request.contact())
            .availableTime(request.availableTime())
            .price(request.price())
            .introduce(request.introduce())
            .experience(request.experience())
            .address(address)
            .member(member)
            .build();
    }

    public Address toAddress(PetSitterRegisterRequest request) {
        return Address.builder()
            .region(request.region())
            .district(request.district())
            .neighborhood(request.neighborhood())
            .detailAddress(request.address())
            .build();
    }

    public AvailablePetType toAvailablePetType(
        PetSitterProfile petSitterProfile, PetType petType
    ) {
        return AvailablePetType.builder()
            .petSitter(petSitterProfile)
            .petType(petType)
            .build();
    }
}
