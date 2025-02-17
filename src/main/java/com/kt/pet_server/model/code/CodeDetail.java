package com.kt.pet_server.model.code;

import com.kt.pet_server.global.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeDetail extends BaseEntity {

    @Id
    @Column(name = "code_id", length = 50, nullable = false, unique = true)
    private String codeId; // 예: M, F, ACTIVE

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private CodeGroup codeGroup; // GENDER, STATUS 등의 그룹

    @Column(name = "code_name", length = 100, nullable = false)
    private String codeName; // 예: 남성, 여성, 활성

    @Column(name = "code_value", length = 50, nullable = false)
    private String codeValue; // 예: M, F, A

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder; // 정렬 순서

    @Column(name = "is_active", nullable = false)
    private Boolean isActive; // 사용 여부 (1: 사용, 0: 비사용)
}
