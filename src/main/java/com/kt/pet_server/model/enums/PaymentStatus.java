package com.kt.pet_server.model.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PaymentStatus {
    PENDING("대기"),
    SUCCESS("성공"),
    FAILED("실패"),
    CANCELLED("취소");

    private final String description;
}
