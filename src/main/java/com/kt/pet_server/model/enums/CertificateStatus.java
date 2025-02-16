package com.kt.pet_server.model.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CertificateStatus {

    READY("준비"),
    PROGRESS("진행 중"),
    APPROVE("승인"),
    REJECT("거절");

    private final String description;

}
