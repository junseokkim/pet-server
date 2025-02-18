package com.kt.pet_server.repository.booking;

import com.kt.pet_server.model.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByIdAndMemberId(Long id, Long memberId);
    List<Booking> findByMemberId(Long memberId);
    List<Booking> findByTimeSlotId(Long timeSlotId);
}
