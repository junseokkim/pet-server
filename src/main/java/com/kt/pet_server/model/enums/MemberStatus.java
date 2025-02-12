package com.kt.pet_server.model.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberStatus {

    ACTIVE("활성화"),
    SUSPENSION("정지"),
    WITHDRAWAL("탈퇴");
    
    private final String description;

}
