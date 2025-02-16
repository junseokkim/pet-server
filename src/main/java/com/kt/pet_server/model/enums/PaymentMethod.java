package com.kt.pet_server.model.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PaymentMethod {
    CREDIT("신용카드"),
    SIMPLE_PAY("간편결제");

    private final String description;
}

