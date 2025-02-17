package com.kt.pet_server.repository.code;

import com.kt.pet_server.global.exception.CustomException;
import com.kt.pet_server.model.code.CodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeGroupRepository extends JpaRepository<CodeGroup, String> {

    default CodeGroup getCodeGroup(String groupId) {
        return findById(groupId)
            .orElseThrow(() -> new CustomException("해당 코드 그룹이 존재하지 않습니다."));
    }
    Boolean existsByGroupId(String groupId);
    Boolean existsByGroupName(String groupName);
}
