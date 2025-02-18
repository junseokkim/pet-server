package com.kt.pet_server.service.impl;

import com.kt.pet_server.dto.request.booking.BookingRequest;
import com.kt.pet_server.dto.response.booking.BookingDetailResponse;
import com.kt.pet_server.dto.response.booking.BookingListResponse;
import com.kt.pet_server.dto.response.booking.BookingResponse;
import com.kt.pet_server.global.exception.CustomException;
import com.kt.pet_server.model.booking.Booking;
import com.kt.pet_server.model.booking.BookingPetMapping;
import com.kt.pet_server.model.booking.Payment;
import com.kt.pet_server.repository.booking.PaymentRepository;
import com.kt.pet_server.model.enums.BookingStatus;
import com.kt.pet_server.model.enums.PaymentStatus;
import com.kt.pet_server.model.enums.PetType;
import com.kt.pet_server.model.member.Member;
import com.kt.pet_server.model.pet.PetProfile;
import com.kt.pet_server.model.petsitter.AvailablePetType;
import com.kt.pet_server.model.petsitter.PetSitterProfile;
import com.kt.pet_server.model.service.TimeSlot;
import com.kt.pet_server.repository.MemberRepository;
import com.kt.pet_server.repository.booking.BookingPetMappingRepository;
import com.kt.pet_server.repository.booking.BookingRepository;
import com.kt.pet_server.repository.pet.PetProfileRepository;
import com.kt.pet_server.repository.petsitter.AvailablePetTypeRepository;
import com.kt.pet_server.repository.service.TimeSlotRepository;
import com.kt.pet_server.service.BookingService;
import com.kt.pet_server.service.PetSitterService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final MemberRepository memberRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final PetProfileRepository petProfileRepository;
    private final BookingPetMappingRepository bookingPetMappingRepository;
    private final AvailablePetTypeRepository availablePetTypeRepository;
    private final PetSitterService petSitterService;
    private final PaymentRepository paymentRepository;

    /**
     * 예약 생성
     */
    @Override
    @Transactional
    public BookingResponse createBooking(Long memberId, BookingRequest request) {
        // 1️⃣ Member 확인
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException("사용자를 찾을 수 없습니다."));

        // 2️⃣ TimeSlot 확인
        List<TimeSlot> timeSlots = timeSlotRepository.findAllById(request.timeSlotIds());
        if (timeSlots.isEmpty() || timeSlots.size() != request.timeSlotIds().size()) {
            throw new CustomException("일부 타임슬롯은 예약할 수 없습니다.");
        }

        // 3️⃣ 펫 프로필 확인
        List<PetProfile> petProfiles = petProfileRepository.findAllById(request.petProfileIds());
        if (petProfiles.isEmpty()) {
            throw new CustomException("펫 프로필이 유효하지 않습니다.");
        }

        // 4️⃣ 펫 타입 유효성 검사
        for (TimeSlot timeSlot : timeSlots) {
            Long petSitterId = timeSlot.getPetSitterService().getPetSitter().getId();
            validatePetTypeForBooking(petSitterId, petProfiles);
        }

        // 5️⃣ Payment 생성 (결제 단건 생성)
        Payment payment = paymentRepository.save(
            Payment.builder()
                .member(member)
                .paymentMethod(request.paymentMethod())
                .amount(request.amount())  // 총 결제 금액
                .status(PaymentStatus.SUCCESS)  // 실제 연동 시 결제 상태 반영
                .paymentAt(LocalDateTime.now())
                .build()
        );

        // 6️⃣ Booking 생성 (다중 타임슬롯에 대해 각각 예약)
        List<Booking> bookings = new ArrayList<>();
        for (TimeSlot timeSlot : timeSlots) {
            if (Boolean.TRUE.equals(timeSlot.getIsBooked())) {
                throw new CustomException(String.format("타임슬롯 '%d'은 이미 예약되었습니다.", timeSlot.getId()));
            }

            // TimeSlot 예약 처리
            timeSlot.setIsBooked(true);
            timeSlotRepository.save(timeSlot);

            // Booking 저장
            Booking booking = bookingRepository.save(
                Booking.builder()
                    .member(member)
                    .timeSlot(timeSlot)
                    .payment(payment)  // Payment 연결
                    .status(BookingStatus.RESERVED)
                    .build()
            );
            bookings.add(booking);

            // BookingPetMapping 저장
            List<BookingPetMapping> petMappings = petProfiles.stream()
                .map(pet -> BookingPetMapping.builder()
                    .booking(booking)
                    .petProfile(pet)
                    .build())
                .toList();
            bookingPetMappingRepository.saveAll(petMappings);
        }

        // ✅ Payment와 Booking 연결 저장
        payment.getBookings().addAll(bookings);
        paymentRepository.save(payment);

        return BookingResponse.from(payment, bookings);
    }


    /**
     * 예약 취소
     */
    @Override
    @Transactional
    public void cancelBooking(Long memberId, Long bookingId) {
        Booking booking = bookingRepository.findByIdAndMemberId(bookingId, memberId)
            .orElseThrow(() -> new CustomException("해당 예약을 취소할 수 없습니다."));

        // TimeSlot 예약 해제
        TimeSlot timeSlot = booking.getTimeSlot();
        timeSlot.setIsBooked(false);
        timeSlotRepository.save(timeSlot);

        // Booking 상태 변경
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    /**
     * 나의 예약 조회
     */
    @Override
    public BookingListResponse<BookingDetailResponse> getMyBookings(Long memberId) {
        List<Booking> bookings = bookingRepository.findByMemberId(memberId);
        return BookingListResponse.from(
            bookings.stream()
                .map(BookingDetailResponse::from)
                .toList()
        );
    }

    /**
     * 특정 TimeSlot의 예약 조회
     */
    @Override
    public BookingListResponse<BookingDetailResponse> getBookingsByTimeSlot(Long timeSlotId) {
        List<Booking> bookings = bookingRepository.findByTimeSlotId(timeSlotId);
        return BookingListResponse.from(
            bookings.stream()
                .map(BookingDetailResponse::from)
                .toList()
        );
    }

    /**
     * 예약 상세 조회
     */
    @Override
    public BookingDetailResponse getBookingDetail(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new CustomException("해당 예약을 찾을 수 없습니다."));
        return BookingDetailResponse.from(booking);
    }

    /**
     * 펫시터가 돌볼 수 없는 펫은 예약 불가
     */
    private void validatePetTypeForBooking(Long petSitterId, List<PetProfile> petProfiles) {
        PetSitterProfile petSitterProfile = petSitterService.loadPetSitterProfile(petSitterId);
        // 펫시터의 가능한 펫 타입 목록 조회
        List<PetType> availablePetTypes = availablePetTypeRepository.findAllByPetSitter(petSitterProfile).stream()
            .map(AvailablePetType::getPetType)
            .toList();

        // 각 펫 프로필의 타입 검사
        for (PetProfile pet : petProfiles) {
            PetType petType = pet.getPetTypeDetail().getPetType();
            if (!availablePetTypes.contains(petType)) {
                throw new CustomException(
                    String.format("펫시터가 '%s' 타입의 반려동물은 돌볼 수 없습니다.", petType.name())
                );
            }
        }
    }

}
