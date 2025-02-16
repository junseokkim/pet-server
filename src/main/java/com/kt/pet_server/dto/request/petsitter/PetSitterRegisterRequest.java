package com.kt.pet_server.dto.request.petsitter;

import com.kt.pet_server.model.enums.PetType;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record PetSitterRegisterRequest(
    
    @NotBlank(message = "이름은 필수 입력값입니다.")
    String name,

    @NotBlank(message = "연락처는 필수 입력값입니다.")
    String contact,

    int price,

    String availableTime,

    String introduce,

    String experience,

    List<PetType> petTypes,

    @NotBlank(message = "지역은 필수 입력값입니다.")
    String region,

    @NotBlank(message = "구역은 필수 입력값입니다.")
    String district,
    
    @NotBlank(message = "동네는 필수 입력값입니다.")
    String neighborhood,

    @NotBlank(message = "주소는 필수 입력값입니다.")
    String address
) {} 