package com.kt.pet_server.service.impl;

import com.kt.pet_server.dto.request.service.ServiceRegisterRequest;
import com.kt.pet_server.dto.response.service.AvailableTimeResponse;
import com.kt.pet_server.dto.response.service.ServiceDetailResponse;
import com.kt.pet_server.dto.response.service.ServiceIdResponse;
import com.kt.pet_server.dto.response.service.ServiceListResponse;
import com.kt.pet_server.dto.response.service.ServiceSummaryResponse;
import com.kt.pet_server.global.exception.CustomException;
import com.kt.pet_server.mapper.ServiceMapper;
import com.kt.pet_server.model.enums.PetSize;
import com.kt.pet_server.model.enums.ServiceType;
import com.kt.pet_server.model.member.Member;
import com.kt.pet_server.model.petsitter.PetSitterProfile;
import com.kt.pet_server.model.service.AvailableSizeMapping;
import com.kt.pet_server.model.service.AvailableTime;
import com.kt.pet_server.model.service.PetSitterService;
import com.kt.pet_server.model.service.ServiceTypeMapping;
import com.kt.pet_server.repository.AvailableSizeRepository;
import com.kt.pet_server.repository.AvailableTimeRepository;
import com.kt.pet_server.repository.PetSitterProfileRepository;
import com.kt.pet_server.repository.PetSitterServiceRepository;
import com.kt.pet_server.repository.ServiceTypeRepository;
import com.kt.pet_server.service.AuthService;
import com.kt.pet_server.service.PetSitterServiceService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PetSitterServiceServiceImpl implements PetSitterServiceService {

    private final ServiceMapper serviceMapper;
    private final AuthService authService;
    private final PetSitterProfileRepository petSitterProfileRepository;
    private final PetSitterServiceRepository petSitterServiceRepository;
    private final AvailableTimeRepository availableTimeRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final AvailableSizeRepository availableSizeRepository;

    @Override
    @Transactional
    public ServiceIdResponse registerService(Long sessionMemberId, ServiceRegisterRequest request) {

        Member member = authService.getMember(sessionMemberId);
        PetSitterProfile petSitterProfile = petSitterProfileRepository.getPetSitterProfileByMember(member);

        PetSitterService petSitterService = petSitterServiceRepository.save(
            serviceMapper.toPetSitterService(petSitterProfile, request)
        );

        // AvailableTime 저장
        List<AvailableTime> availableTimes = request.availableTimes().stream()
            .map(timeRequest -> serviceMapper.toAvailableTime(
                petSitterService,
                timeRequest.startTime(),
                timeRequest.endTime()
            ))
            .toList();
        petSitterService.setAvailableTimes(availableTimeRepository.saveAll(availableTimes));

        // ServiceType 저장
        List<ServiceTypeMapping> serviceTypeMappings = request.serviceTypes().stream()
            .map(serviceType -> serviceMapper.toServiceTypeMapping(serviceType, petSitterService))
            .toList();
        petSitterService.setServiceTypes(serviceTypeRepository.saveAll(serviceTypeMappings));

        // AvailableSize 저장
        List<AvailableSizeMapping> availableSizeMappings = request.availableSizes().stream()
            .map(size -> serviceMapper.toAvailableSizeMapping(size, petSitterService))
            .toList();
        petSitterService.setAvailableSizes(availableSizeRepository.saveAll(availableSizeMappings));

        return ServiceIdResponse.from(petSitterService.getId());
    }

    @Override
    @Transactional
    public ServiceIdResponse cancelService(Long sessionMemberId, Long serviceId) {
        // 서비스 확인
        PetSitterService petSitterService = petSitterServiceRepository.getPetSitterService(serviceId);

        // 펫시터 본인 확인
        if (!petSitterService.getPetSitter().getMember().getId().equals(sessionMemberId)) {
            throw new CustomException("본인의 서비스만 취소할 수 있습니다.");
        }

        petSitterServiceRepository.delete(petSitterService);

        return ServiceIdResponse.from(serviceId);
    }

    /**
     * 나의 펫시터 서비스 조회
     */
    @Override
    public ServiceListResponse<ServiceSummaryResponse> getMyServices(Long sessionMemberId) {
        Member member = authService.getMember(sessionMemberId);
        // 본인 펫시터 프로필 가져오기
        PetSitterProfile petSitterProfile = petSitterProfileRepository.getPetSitterProfileByMember(member);

        // 본인 서비스 조회
        List<PetSitterService> services = petSitterServiceRepository.findByPetSitter(petSitterProfile);

        List<ServiceSummaryResponse> responses = services.stream()
            .map(this::mapToServiceSummaryResponse)
            .toList();

        return ServiceListResponse.from(responses);
    }

    /**
     * 특정 펫시터의 서비스 리스트 조회
     */
    @Override
    public ServiceListResponse<ServiceSummaryResponse> getServices(Long petSitterId) {
        // 특정 펫시터 프로필 가져오기
        PetSitterProfile petSitterProfile = petSitterProfileRepository.getPetSitterProfile(petSitterId);

        // 해당 펫시터의 서비스 조회
        List<PetSitterService> services = petSitterServiceRepository.findByPetSitter(petSitterProfile);

        List<ServiceSummaryResponse> responses = services.stream()
            .map(this::mapToServiceSummaryResponse)
            .toList();

        return ServiceListResponse.from(responses);
    }

    /**
     * 월별 펫시터 서비스 조회 (startDate 기준으로 정렬)
     */
    @Override
    public ServiceListResponse<ServiceSummaryResponse> getMonthlyServices(String year, String month) {
        LocalDate startDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        // 해당 월의 서비스 조회
        List<PetSitterService> services =
            petSitterServiceRepository.findByAvailableStartDateBetweenOrderByAvailableStartDate(startDate, endDate);

        List<ServiceSummaryResponse> responses = services.stream()
            .map(this::mapToServiceSummaryResponse)
            .toList();

        return ServiceListResponse.from(responses);
    }

    /**
     * 펫시터 서비스 상세 조회
     */
    @Override
    public ServiceDetailResponse getService(Long serviceId) {
        // 서비스 정보 조회
        PetSitterService service = petSitterServiceRepository.getPetSitterService(serviceId);

        // 서비스 타입 조회
        List<ServiceType> serviceTypes = service.getServiceTypes().stream()
            .map(ServiceTypeMapping::getType)
            .toList();

        // 제공 가능 크기 조회
        List<PetSize> availableSizes = service.getAvailableSizes().stream()
            .map(AvailableSizeMapping::getSize)
            .toList();

        // 제공 가능 시간 조회
        List<AvailableTimeResponse> availableTimes = service.getAvailableTimes().stream()
            .map(AvailableTimeResponse::from)
            .toList();

        return ServiceDetailResponse.from(service, serviceTypes, availableSizes, availableTimes);
    }

    /**
     * 공통 ServiceSummaryResponse 매핑 메서드
     */
    private ServiceSummaryResponse mapToServiceSummaryResponse(PetSitterService service) {
        // 서비스 타입 조회
        List<ServiceType> serviceTypes = service.getServiceTypes().stream()
            .map(ServiceTypeMapping::getType)
            .toList();

        // 제공 가능 크기 조회
        List<PetSize> availableSizes = service.getAvailableSizes().stream()
            .map(AvailableSizeMapping::getSize)
            .toList();

        // 제공 가능 시간 조회 (startDate 기준으로 정렬)
        List<AvailableTimeResponse> availableTimes = service.getAvailableTimes().stream()
            .sorted((a, b) -> a.getAvailableDate().compareTo(b.getAvailableDate()))
            .map(AvailableTimeResponse::from)
            .toList();

        return ServiceSummaryResponse.from(service, serviceTypes, availableSizes, availableTimes);
    }
}
