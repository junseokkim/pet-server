package com.kt.pet_server.model.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ScheduleStatus {
    APPLY("신청"),
    ACCEPT("승인"),
    REJECT("거부"),
    PROGRESS("진행 중"),
    FINISH("완료");
    private final String description;
}
