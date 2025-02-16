package com.kt.pet_server.dto.request.service;

import java.time.LocalDateTime;

public record AvailableTimeRequest(
    LocalDateTime startTime,
    LocalDateTime endTime
) {
}
