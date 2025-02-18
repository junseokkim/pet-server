package com.kt.pet_server.dto.response.service;

import com.kt.pet_server.model.service.TimeSlot;
import java.time.LocalDate;
import java.time.LocalTime;

public record AvailableTimeResponse(
    Long timeSlotId,
    LocalDate date,
    LocalTime startTime,
    LocalTime endTime,
    boolean isBooked
) {

    public static AvailableTimeResponse from(TimeSlot timeSlot) {
        return new AvailableTimeResponse(
            timeSlot.getId(),
            timeSlot.getDate(),
            timeSlot.getStartTime(),
            timeSlot.getEndTime(),
            timeSlot.getIsBooked()
        );
    }
}
