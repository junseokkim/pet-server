package com.kt.pet_server.model.service;

import com.kt.pet_server.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeSlot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 시간대가 어느 서비스에 속하는지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private PetSitterService petSitterService;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    @Setter
    private Boolean isBooked;  // 해당 시간 슬롯의 예약 여부
}
