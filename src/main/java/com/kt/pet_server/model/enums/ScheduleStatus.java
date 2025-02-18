package com.kt.pet_server.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScheduleStatus {
    APPLY("신청"),
    ACCEPT("승인"),
    REJECT("거부"),
    PROGRESS("결제 진행 중"),
    FINISH("완료");
    private final String description;
}
