package com.kt.pet_server.service;

import com.kt.pet_server.dto.request.code_detail.CodeDetailCreateRequest;
import com.kt.pet_server.dto.request.code_detail.CodeDetailUpdateRequest;
import com.kt.pet_server.dto.response.code_detail.CodeDetailIdResponse;
import com.kt.pet_server.dto.response.code_detail.CodeDetailListResponse;
import com.kt.pet_server.dto.response.code_detail.CodeDetailResponse;
import com.kt.pet_server.dto.response.code_detail.CodeDetailSummaryResponse;
import com.kt.pet_server.model.code.CodeDetail;
import jakarta.servlet.http.HttpSession;

public interface CodeDetailService {
    CodeDetailIdResponse createCodeDetail(Long sessionMemberId, CodeDetailCreateRequest request);
    CodeDetailIdResponse updateCodeDetail(Long sessionMemberId, CodeDetailUpdateRequest request);
    CodeDetailIdResponse deleteCodeDetail(Long sessionMemberId, String codeDetailId);
    CodeDetailResponse getCodeDetail(Long sessionMemberId, String codeDetailId);
    CodeDetailListResponse<CodeDetailResponse> getCodeDetailsByGroup(Long sessionMemberId, String codeGroupId);
    CodeDetail getCodeDetailByName(String codeDetailName);

}
