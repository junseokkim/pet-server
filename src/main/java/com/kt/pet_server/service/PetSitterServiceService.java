package com.kt.pet_server.service;

import com.kt.pet_server.dto.request.service.ServiceRegisterRequest;
import com.kt.pet_server.dto.response.service.ServiceDetailResponse;
import com.kt.pet_server.dto.response.service.ServiceIdResponse;
import com.kt.pet_server.dto.response.service.ServiceListResponse;
import com.kt.pet_server.dto.response.service.ServiceSummaryResponse;

public interface PetSitterServiceService {
    ServiceIdResponse registerService(Long sessionMemberId, ServiceRegisterRequest request);
    ServiceIdResponse cancelService(Long sessionMemberId, Long serviceId);
    ServiceListResponse<ServiceSummaryResponse> getMyServices(Long sessionMemberId);
    ServiceListResponse<ServiceSummaryResponse> getServices(Long petSitterId);
    ServiceListResponse<ServiceSummaryResponse> getMonthlyServices(String year, String month);
    ServiceDetailResponse getService(Long serviceId);
}
