package com.kt.pet_server.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PetSize {

    LARGE("대형"),
    MEDIUM("중형"),
    SMALL("소형"),
    ALL("전부");

    private final String description;
}
