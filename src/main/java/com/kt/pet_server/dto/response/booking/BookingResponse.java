package com.kt.pet_server.dto.response.booking;

import com.kt.pet_server.model.booking.Booking;
import com.kt.pet_server.model.booking.Payment;
import com.kt.pet_server.model.enums.PaymentMethod;
import com.kt.pet_server.model.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

public record BookingResponse(
    Long paymentId,
    PaymentMethod paymentMethod,
    PaymentStatus paymentStatus,
    Long amount,
    LocalDateTime paymentAt,
    List<Long> bookingIds,
    List<Long> timeSlotIds
) {
    public static BookingResponse from(Payment payment, List<Booking> bookings) {
        List<Long> bookingIds = bookings.stream().map(Booking::getId).toList();
        List<Long> timeSlotIds = bookings.stream().map(b -> b.getTimeSlot().getId()).toList();

        return new BookingResponse(
            payment.getId(),
            payment.getPaymentMethod(),
            payment.getStatus(),
            payment.getAmount(),
            payment.getPaymentAt(),
            bookingIds,
            timeSlotIds
        );
    }
}
