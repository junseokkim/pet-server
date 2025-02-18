package com.kt.pet_server.model.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BookingStatus {
    RESERVED("예약 완료"),
    CANCELLED("예약 취소"),
    COMPLETED("서비스 완료");

    private final String description;
}
