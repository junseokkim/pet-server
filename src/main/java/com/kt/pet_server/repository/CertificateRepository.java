package com.kt.pet_server.repository;

import com.kt.pet_server.model.petsitter.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
