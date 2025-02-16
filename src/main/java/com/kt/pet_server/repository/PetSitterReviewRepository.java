package com.kt.pet_server.repository;

import com.kt.pet_server.model.petsitter.PetSitterReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetSitterReviewRepository extends JpaRepository<PetSitterReview, Long> {
}
