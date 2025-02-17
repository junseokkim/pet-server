package com.kt.pet_server.model.code;

import com.kt.pet_server.dto.request.code_detail.CodeDetailUpdateRequest;
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
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeDetail extends BaseEntity {

    @Id
    @Column(length = 50, nullable = false, unique = true)
    private String codeDetailId; // 예: M, F, ACTIVE

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private CodeGroup codeGroup; // GENDER, STATUS 등의 그룹

    @Column(length = 100, nullable = false)
    private String codeDetailName; // 예: 남성, 여성, 활성

    @Column(length = 50, nullable = false)
    private String codeDetailValue; // 예: M, F, A

    @Setter
    @Column(nullable = false)
    private Integer sortOrder; // 정렬 순서

    @Column(nullable = false)
    private Boolean isActive; // 사용 여부 (1: 사용, 0: 비사용)

    public void update(CodeDetailUpdateRequest request) {
        this.codeDetailName = request.codeDetailName();
        this.codeDetailValue = request.codeDetailValue();
        this.sortOrder = request.sortOrder();
    }
}
