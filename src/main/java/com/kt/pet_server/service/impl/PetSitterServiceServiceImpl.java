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
import com.kt.pet_server.model.service.PetSitterService;
import com.kt.pet_server.model.service.ServiceTypeMapping;
import com.kt.pet_server.model.service.TimeSlot;
import com.kt.pet_server.repository.service.AvailableSizeRepository;
import com.kt.pet_server.repository.service.TimeSlotRepository;
import com.kt.pet_server.repository.petsitter.PetSitterProfileRepository;
import com.kt.pet_server.repository.service.PetSitterServiceRepository;
import com.kt.pet_server.repository.service.ServiceTypeRepository;
import com.kt.pet_server.service.AuthService;
import com.kt.pet_server.service.PetSitterServiceService;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
    private final TimeSlotRepository timeSlotRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final AvailableSizeRepository availableSizeRepository;

    @Override
    @Transactional
    public ServiceIdResponse registerService(Long sessionMemberId, ServiceRegisterRequest request) {

        Member member = authService.getMember(sessionMemberId);
        PetSitterProfile petSitterProfile = petSitterProfileRepository.getPetSitterProfileByMember(member);

        // PetSitterService 생성
        PetSitterService petSitterService = petSitterServiceRepository.save(
            serviceMapper.toPetSitterService(petSitterProfile, request)
        );

        // ✅ 1시간 단위 TimeSlot 자동 생성
        List<TimeSlot> timeSlots = generateHourlyTimeSlots(petSitterService);
        petSitterService.setTimeSlots(timeSlotRepository.saveAll(timeSlots));

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
        PetSitterProfile petSitterProfile = petSitterProfileRepository.getPetSitterProfileByMember(member);
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
        LocalDate startDate = validateYearAndMonth(year, month);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        // 해당 월의 TimeSlot을 기반으로 서비스 조회
        List<PetSitterService> services = petSitterServiceRepository.findByTimeSlotsBetween(startDate, endDate);

        List<ServiceSummaryResponse> responses = services.stream()
            .map(this::mapToServiceSummaryResponse)
            .toList();


        return ServiceListResponse.from(responses);
    }

    /**
     * 펫시터 서비스 상세 조회
     */
    @Override
    public ServiceDetailResponse getService(Long sessionMemberId, Long serviceId) {
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

        // ✅ 제공 가능 TimeSlot (예약 가능한 것만)
        List<AvailableTimeResponse> timeSlotResponses = service.getTimeSlots().stream()
            .filter(slot -> !slot.getIsBooked()) // 예약되지 않은 것만 반환
            .map(AvailableTimeResponse::from)
            .toList();

        Boolean isMine = service.getPetSitter().getMember().getId().equals(sessionMemberId);

        return ServiceDetailResponse.from(service, serviceTypes, availableSizes, timeSlotResponses, isMine);
    }

    /**
     * 공통 ServiceSummaryResponse 매핑 메서드
     */
    /**
     * 공통 ServiceSummaryResponse 매핑 메서드 (TimeSlot 기반)
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

        // 제공 가능 TimeSlot 조회 (date 및 startTime 기준으로 정렬)
        List<AvailableTimeResponse> availableTimes = service.getTimeSlots().stream()
            .sorted((a, b) -> {
                int dateCompare = a.getDate().compareTo(b.getDate());
                if (dateCompare == 0) {
                    return a.getStartTime().compareTo(b.getStartTime());
                }
                return dateCompare;
            })
            .map(AvailableTimeResponse::from)
            .toList();

        return ServiceSummaryResponse.from(service, serviceTypes, availableSizes, availableTimes);
    }

    /**
     * 1시간 단위 TimeSlot 생성
     */
    private List<TimeSlot> generateHourlyTimeSlots(PetSitterService service) {
        List<TimeSlot> timeSlots = new ArrayList<>();
        for (LocalDate date = service.getAvailableStartDate();
             !date.isAfter(service.getAvailableEndDate());
             date = date.plusDays(1)) {
            for (int hour = 9; hour < 18; hour++) { // 09:00 ~ 18:00 (운영시간 예시)
                timeSlots.add(TimeSlot.builder()
                    .petSitterService(service)
                    .date(date)
                    .startTime(LocalTime.of(hour, 0))
                    .endTime(LocalTime.of(hour + 1, 0))
                    .isBooked(false)
                    .build());
            }
        }
        return timeSlots;
    }

    private LocalDate validateYearAndMonth(String year, String month) {
        try {
            int yearValue = Integer.parseInt(year);
            int monthValue = Integer.parseInt(month);

            // 유효한 월인지 검사 (1~12)
            if (monthValue < 1 || monthValue > 12) {
                throw new CustomException("월(month)은 1부터 12 사이의 값이어야 합니다.");
            }

            return LocalDate.of(yearValue, monthValue, 1);
        } catch (NumberFormatException e) {
            throw new CustomException("연도(year)와 월(month)은 숫자 형식이어야 합니다.");
        } catch (DateTimeException e) {
            throw new CustomException("잘못된 연도(year) 또는 월(month) 값입니다.");
        }
    }

}
