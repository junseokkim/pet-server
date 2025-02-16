package com.kt.pet_server.dto.response.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.kt.pet_server.model.service.AvailableTime;
import java.time.LocalTime;

public record AvailableTimeResponse(
    LocalDate availableDate,
    LocalTime startTime,
    LocalTime endTime
) {

    public static AvailableTimeResponse from(AvailableTime availableTime) {
        return new AvailableTimeResponse(
            availableTime.getAvailableDate(),
            availableTime.getStartTime(),
            availableTime.getEndTime()
        );
    }
}
