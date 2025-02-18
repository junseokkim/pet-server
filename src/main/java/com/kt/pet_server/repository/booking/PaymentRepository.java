package com.kt.pet_server.repository.booking;

import com.kt.pet_server.model.booking.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
