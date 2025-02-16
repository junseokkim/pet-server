package com.kt.pet_server.model.petsitter;

import com.kt.pet_server.global.base.BaseEntity;
import com.kt.pet_server.model.address.Address;
import com.kt.pet_server.model.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetSitterProfile extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    private String availableTime;

    @Column(nullable = false)
    private Integer price;

    private String introduce;

    private String experience;

    private String profileUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Address address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Certificate certificate;

}
