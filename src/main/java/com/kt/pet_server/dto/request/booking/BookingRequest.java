package com.kt.pet_server.dto.request.booking;

import com.kt.pet_server.model.enums.PaymentMethod;
import java.util.List;


public record BookingRequest(
    List<Long> timeSlotIds,
    List<Long> petProfileIds,
    PaymentMethod paymentMethod,
    Long amount
) {}
