package com.kt.pet_server.repository.booking;

import com.kt.pet_server.model.booking.BookingPetMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingPetMappingRepository extends JpaRepository<BookingPetMapping, Long> {
}
