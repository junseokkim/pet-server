package com.kt.pet_server.model.code;

import com.kt.pet_server.dto.request.code_group.CodeGroupUpdateRequest;
import com.kt.pet_server.global.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class CodeGroup extends BaseEntity {

    @Id
    @Column(name = "group_id", length = 50, nullable = false, unique = true)
    private String groupId; // 예: GENDER, STATUS

    @Column(name = "group_name", length = 100, nullable = false)
    private String groupName; // 예: 성별 코드, 상태 코드

    @Column(name = "description", length = 255)
    private String description; // 코드 그룹 설명

    @Builder.Default
    @OneToMany(mappedBy = "codeGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CodeDetail> codeDetails = new ArrayList<>();

    public void update(CodeGroupUpdateRequest request) {
        this.groupId = request.codeGroup();
        this.groupName = request.codeGroupName();
        this.description = request.codeGroupDescription();
    }

    public void addCodeDetail(CodeDetail codeDetail) {
        if (this.codeDetails == null) {
            this.codeDetails = new ArrayList<>();
        }
        this.codeDetails.add(codeDetail);
    }
}
