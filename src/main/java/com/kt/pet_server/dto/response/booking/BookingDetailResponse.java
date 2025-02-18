package com.kt.pet_server.dto.response.booking;

import com.kt.pet_server.model.booking.Booking;
import com.kt.pet_server.model.booking.BookingPetMapping;

import java.time.LocalDateTime;
import java.util.List;

public record BookingDetailResponse(
    Long bookingId,
    Long memberId,
    Long timeSlotId,
    List<String> petNames,
    String status,
    LocalDateTime createdAt
) {

    public static BookingDetailResponse from(Booking booking) {
        List<String> petNames = booking.getSchedulePetMappings().stream()
            .map(BookingPetMapping::getPetProfile)
            .map(pet -> pet.getName() + " (" + pet.getBirth().getYear() + "년생)")
            .toList();

        return new BookingDetailResponse(
            booking.getId(),
            booking.getMember().getId(),
            booking.getTimeSlot().getId(),
            petNames,
            booking.getStatus().name(),
            booking.getCreatedAt()
        );
    }
}
