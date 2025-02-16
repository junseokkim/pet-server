package com.kt.pet_server.model.service;

import com.kt.pet_server.global.base.BaseEntity;
import com.kt.pet_server.model.petsitter.PetSitterProfile;
import com.kt.pet_server.model.schedule.Schedule;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetSitterService extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer hourlyPrice;

    @Column(nullable = false)
    private LocalDate availableStartDate;

    @Column(nullable = false)
    private LocalDate availableEndDate;

    @Column(nullable = false)
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private PetSitterProfile petSitter;

    @Builder.Default
    @Setter
    @OneToMany(mappedBy = "petSitterService", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvailableTime> availableTimes = new ArrayList<>();

    @Builder.Default
    @Setter
    @OneToMany(mappedBy = "petSitterService", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

    @Builder.Default
    @Setter
    @OneToMany(mappedBy = "petSitterService", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceTypeMapping> serviceTypes = new ArrayList<>();

    @Builder.Default
    @Setter
    @OneToMany(mappedBy = "petSitterService", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvailableSizeMapping> availableSizes = new ArrayList<>();

}