package com.kt.pet_server.service;

import com.kt.pet_server.dto.request.code_group.CodeGroupUpdateRequest;
import com.kt.pet_server.dto.response.code_group.CodeGroupIdResponse;
import com.kt.pet_server.dto.response.code_group.CodeGroupInquiryResponse;
import com.kt.pet_server.dto.response.code_group.CodeGroupListResponse;
import com.kt.pet_server.model.code.CodeGroup;
import jakarta.servlet.http.HttpSession;
import java.util.List;

public interface CodeGroupService {
    CodeGroupIdResponse createCodeGroup(Long sessionMemberId, CodeGroupUpdateRequest request);
    CodeGroupIdResponse updateCodeGroup(Long sessionMemberId, CodeGroupUpdateRequest request);
    CodeGroupIdResponse deleteCodeGroup(Long sessionMemberId, String groupId);
    CodeGroupListResponse<CodeGroupInquiryResponse> getCodeGroupList(Long sessionMemberId);
    CodeGroup getCodeGroup(String groupId);
}
