package com.kt.pet_server.service;

import com.kt.pet_server.dto.request.code_group.CodeGroupUpdateRequest;
import com.kt.pet_server.dto.response.code_group.CodeGroupIdResponse;
import com.kt.pet_server.dto.response.code_group.CodeGroupInquiryResponse;
import com.kt.pet_server.dto.response.code_group.CodeGroupListResponse;
import jakarta.websocket.Session;

public interface CodeGroupService {
    CodeGroupIdResponse createCodeGroup(Session session, CodeGroupUpdateRequest request);
    CodeGroupIdResponse updateCodeGroup(Session session, String groupId, CodeGroupUpdateRequest request);
    CodeGroupIdResponse deleteCodeGroup(Session session, String groupId);
    CodeGroupListResponse<CodeGroupInquiryResponse> getCodeGroupList(Session session);
}
