package com.kt.pet_server.service.impl;

import com.kt.pet_server.dto.request.code_detail.CodeDetailCreateRequest;
import com.kt.pet_server.dto.request.code_detail.CodeDetailUpdateRequest;
import com.kt.pet_server.dto.response.code_detail.CodeDetailIdResponse;
import com.kt.pet_server.dto.response.code_detail.CodeDetailListResponse;
import com.kt.pet_server.dto.response.code_detail.CodeDetailResponse;
import com.kt.pet_server.global.exception.CustomException;
import com.kt.pet_server.model.code.CodeDetail;
import com.kt.pet_server.model.code.CodeGroup;
import com.kt.pet_server.repository.code.CodeDetailRepository;
import com.kt.pet_server.service.AuthService;
import com.kt.pet_server.service.CodeDetailService;
import com.kt.pet_server.service.CodeGroupService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CodeDetailServiceImpl implements CodeDetailService {

    private final CodeDetailRepository codeDetailRepository;
    private final CodeGroupService codeGroupService;
    private final AuthService authService;

    @Override
    @Transactional
    public CodeDetailIdResponse createCodeDetail(Long sessionMemberId, CodeDetailCreateRequest request) {

        authService.checkAdmin(sessionMemberId);
        CodeGroup codeGroup = codeGroupService.getCodeGroup(request.codeGroupId());

        if (codeDetailRepository.existsByCodeDetailId(request.codeDetailId())) {
            throw new CustomException("이미 존재하는 코드 디테일 아이디입니다.");
        }

        if (codeDetailRepository.existsByCodeDetailName(request.codeDetailName())) {
            throw new CustomException("이미 존재하는 코드 디테일 이름입니다.");
        }

        if (codeDetailRepository.existsByCodeDetailValue(request.codeDetailValue())) {
            throw new CustomException("이미 존재하는 코드 디테일 값입니다.");
        }

        validateSortOrder(codeGroup, request.sortOrder());
        shiftSortOrderForward(codeGroup, request.sortOrder());

        CodeDetail codeDetail = codeDetailRepository.save(CodeDetailCreateRequest.toEntity(request, codeGroup));
        codeGroup.addCodeDetail(codeDetail);
        return CodeDetailIdResponse.from(codeDetail.getCodeDetailId());
    }

    @Override
    @Transactional
    public CodeDetailIdResponse updateCodeDetail(
        Long sessionMemberId, CodeDetailUpdateRequest request
    ) {

        authService.checkAdmin(sessionMemberId);

        CodeDetail codeDetail = codeDetailRepository.getCodeDetail(request.codeDetailId());

        if (codeDetailRepository.existsByCodeDetailName(request.codeDetailName())
            && !codeDetail.getCodeDetailName().equals(request.codeDetailName())) {
            throw new CustomException("이미 존재하는 코드 디테일 이름입니다.");
        }

        if (codeDetailRepository.existsByCodeDetailValue(request.codeDetailValue())
            && !codeDetail.getCodeDetailValue().equals(request.codeDetailValue())) {
            throw new CustomException("이미 존재하는 코드 디테일 값입니다.");
        }

        validateSortOrder(codeDetail.getCodeGroup(), request.sortOrder());

        if (!request.sortOrder().equals(codeDetail.getSortOrder())) {
            // 정렬 이동 처리
            moveSortOrder(codeDetail.getCodeGroup(), codeDetail.getSortOrder(), request.sortOrder());
        }

        codeDetail.update(request);

        return CodeDetailIdResponse.from(codeDetail.getCodeDetailId());
    }

    @Override
    @Transactional
    public CodeDetailIdResponse deleteCodeDetail(Long sessionMemberId, String codeDetailId) {

        authService.checkAdmin(sessionMemberId);

        CodeDetail codeDetail = codeDetailRepository.getCodeDetail(codeDetailId);
        int deletedSortOrder = codeDetail.getSortOrder();
        CodeGroup codeGroup = codeDetail.getCodeGroup();

        codeGroup.removeCodeDetail(codeDetail);
        codeDetailRepository.delete(codeDetail);

        // 삭제 후 뒷순서 당기기
        shiftSortOrderBackward(codeGroup, deletedSortOrder);

        return new CodeDetailIdResponse(codeDetailId);
    }

    @Override
    public CodeDetailResponse getCodeDetail(Long sessionMemberId, String codeDetailId) {

        authService.checkAdmin(sessionMemberId);

        CodeDetail codeDetail = codeDetailRepository.getCodeDetail(codeDetailId);
        return CodeDetailResponse.from(codeDetail);
    }

    @Override
    public CodeDetailListResponse<CodeDetailResponse> getCodeDetailsByGroup(
        Long sessionMemberId, String codeGroupId
    ) {
        authService.checkAdmin(sessionMemberId);

        CodeGroup codeGroup = codeGroupService.getCodeGroup(codeGroupId);

        List<CodeDetailResponse> responses = codeGroup.getCodeDetails().stream()
            .sorted((a, b) -> a.getSortOrder().compareTo(b.getSortOrder()))
            .map(CodeDetailResponse::from)
            .toList();

        return new CodeDetailListResponse<>(responses);
    }

    @Override
    public CodeDetail getCodeDetailByName(String codeDetailName) {
        return codeDetailRepository.findByCodeDetailName(codeDetailName)
            .orElseThrow(() -> new CustomException("해당 상세 코드명이 존재하지 않습니다."));
    }

    private void validateSortOrder(CodeGroup codeGroup, int sortOrder) {
        List<CodeDetail> groupCodeDetails = codeDetailRepository.findAllByCodeGroup(codeGroup);
        if (sortOrder < 1 || sortOrder > groupCodeDetails.size()) {
            throw new CustomException("정렬 순서는 1 이상이며 현재 코드 개수보다 클 수 없습니다.");
        }
    }

    private void shiftSortOrderForward(CodeGroup codeGroup, int startSortOrder) {
        List<CodeDetail> codesToShift = codeDetailRepository.findAllByCodeGroup(codeGroup).stream()
            .filter(c -> c.getSortOrder() >= startSortOrder)
            .sorted((a, b) -> a.getSortOrder().compareTo(b.getSortOrder()))
            .toList();

        for (CodeDetail code : codesToShift) {
            code.setSortOrder(code.getSortOrder() + 1);
        }
    }

    private void shiftSortOrderBackward(CodeGroup codeGroup, int startSortOrder) {
        List<CodeDetail> codesToShift = codeDetailRepository.findAllByCodeGroup(codeGroup).stream()
            .filter(c -> c.getSortOrder() > startSortOrder)
            .sorted((a, b) -> a.getSortOrder().compareTo(b.getSortOrder()))
            .toList();

        for (CodeDetail code : codesToShift) {
            code.setSortOrder(code.getSortOrder() - 1);
        }
    }

    private void moveSortOrder(CodeGroup codeGroup, int oldSortOrder, int newSortOrder) {
        if (oldSortOrder < newSortOrder) {
            // 앞으로 이동 (뒤에 있는 것들 당기기)
            shiftSortOrderBackward(codeGroup, oldSortOrder);
            shiftSortOrderForward(codeGroup, newSortOrder);
        } else if (oldSortOrder > newSortOrder) {
            // 뒤로 이동 (앞에 있는 것들 밀기)
            shiftSortOrderForward(codeGroup, newSortOrder);
            shiftSortOrderBackward(codeGroup, oldSortOrder);
        }
    }
}
