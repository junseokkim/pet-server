package com.kt.pet_server.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceType {

    WALK("산책"),
    VISITING_CARE("방문 돌봄"),
    FOSTER_CARE("위탁 돌봄"),
    ALL("전부");

    private final String description;
}
