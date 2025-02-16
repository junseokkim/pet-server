package com.kt.pet_server.dto.response.petsitter;

public record PetSitterResponse(
    Long petSitterId,
    String name,
    String contact,
    String address,
    String profileImageUrl,
    int price,
    String introduce,
    String experience
) {} 