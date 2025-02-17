package com.kt.pet_server.repository.code;

import com.kt.pet_server.global.exception.CustomException;
import com.kt.pet_server.model.code.CodeDetail;
import com.kt.pet_server.model.code.CodeGroup;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeDetailRepository extends JpaRepository<CodeDetail, String > {

    default CodeDetail getCodeDetail(String codeDetailId) {
        return findById(codeDetailId)
            .orElseThrow(() -> new CustomException("해당 코드 디테일이 존재하지 않습니다."));
    }
    Boolean existsByCodeDetailId(String codeDetailId);
    Boolean existsByCodeDetailName(String codeDetailName);
    Boolean existsByCodeDetailValue(String codeDetailValue);
    List<CodeDetail> findAllByCodeGroup(CodeGroup codeGroup);
    Optional<CodeDetail> findByCodeDetailName(String codeDetailName);
}
