package com.kt.pet_server.model.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PaymentStatus {
    PENDING("결제 대기"),
    SUCCESS("결제 완료"),
    FAILED("결제 실패"),
    REFUNDED("환불 완료");

    private final String description;
}
