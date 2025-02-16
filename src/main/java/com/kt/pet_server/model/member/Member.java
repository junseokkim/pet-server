package com.kt.pet_server.model.member;

import com.kt.pet_server.dto.request.member.MemberUpdateRequest;
import com.kt.pet_server.global.base.BaseEntity;
import com.kt.pet_server.model.enums.MemberStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    private MemberStatus status;
 
    public void update(MemberUpdateRequest request) {
        this.name = request.name();
        this.contact = request.contact();
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
