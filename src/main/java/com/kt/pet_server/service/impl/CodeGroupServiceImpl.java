package com.kt.pet_server.service.impl;

import com.kt.pet_server.dto.request.code_group.CodeGroupUpdateRequest;
import com.kt.pet_server.dto.response.code_group.CodeGroupIdResponse;
import com.kt.pet_server.dto.response.code_group.CodeGroupInquiryResponse;
import com.kt.pet_server.dto.response.code_group.CodeGroupListResponse;
import com.kt.pet_server.global.exception.CustomException;
import com.kt.pet_server.model.code.CodeGroup;
import com.kt.pet_server.repository.code.CodeGroupRepository;
import com.kt.pet_server.service.CodeGroupService;
import jakarta.websocket.Session;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CodeGroupServiceImpl implements CodeGroupService {

    private final CodeGroupRepository codeGroupRepository;

    @Override
    public CodeGroupIdResponse createCodeGroup(Session session, CodeGroupUpdateRequest request) {

        // TODO: 관리자 권한 확인

        if (codeGroupRepository.existsByGroupId(request.codeGroup())) {
            throw new CustomException("이미 존재하는 코드 그룹입니다.");
        }
        CodeGroup codeGroup = CodeGroupUpdateRequest.toEntity(request);

        return CodeGroupIdResponse.from(codeGroupRepository.save(codeGroup).getGroupId());
    }

    @Override
    @Transactional
    public CodeGroupIdResponse updateCodeGroup(Session session, String groupId, CodeGroupUpdateRequest request) {

        // TODO: 관리자 권한 확인
        CodeGroup codeGroup = codeGroupRepository.getCodeGroup(groupId);

        if (codeGroupRepository.existsByGroupId(request.codeGroup())
            && !codeGroup.getGroupId().equals(request.codeGroup())) {
            throw new CustomException("이미 존재하는 코드 그룹 ID입니다.");
        }

        if (codeGroupRepository.existsByGroupName(request.codeGroupName())
            && !codeGroup.getGroupName().equals(request.codeGroupName())) {
            throw new CustomException("이미 존재하는 코드 그룹 이름입니다.");
        }

        codeGroup.update(request);
        return CodeGroupIdResponse.from(codeGroup.getGroupId());
    }

    @Override
    public CodeGroupIdResponse deleteCodeGroup(Session session, String groupId) {

        // TODO: 관리자 권한 확인
        CodeGroup codeGroup = codeGroupRepository.getCodeGroup(groupId);

        if (!codeGroup.getCodeDetails().isEmpty()) {
            throw new CustomException("코드 그룹에 속한 코드 상세가 존재합니다.");
        }
        codeGroupRepository.delete(codeGroup);
        return CodeGroupIdResponse.from(codeGroup.getGroupId());
    }

    @Override
    public CodeGroupListResponse<CodeGroupInquiryResponse> getCodeGroupList(Session session) {

        // TODO: 관리자 권한 확인

        List<CodeGroup> codeGroups = codeGroupRepository.findAll();

        return CodeGroupListResponse.from(
            codeGroups.stream().map(CodeGroupInquiryResponse::from).toList()
        );
    }
}
