package com.kt.pet_server.controller;

import com.kt.pet_server.dto.request.booking.BookingRequest;
import com.kt.pet_server.dto.response.booking.BookingDetailResponse;
import com.kt.pet_server.dto.response.booking.BookingListResponse;
import com.kt.pet_server.dto.response.booking.BookingResponse;
import com.kt.pet_server.global.base.BaseResponse;
import com.kt.pet_server.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "예약 API", description = "예약 API 목록")
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @Operation(summary = "예약 생성")
    @PostMapping
    public BaseResponse<BookingResponse> createBooking(
        HttpSession session,
        @RequestBody BookingRequest request
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");

        BookingResponse response = bookingService.createBooking(sessionMemberId, request);
        return BaseResponse.onSuccess("예약 생성 성공", response);
    }

    @Operation(summary = "예약 취소")
    @DeleteMapping("/{bookingId}")
    public BaseResponse<Void> cancelBooking(
        HttpSession session,
        @PathVariable Long bookingId
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");

        bookingService.cancelBooking(sessionMemberId, bookingId);
        return BaseResponse.onSuccess(
            "예약 취소 성공", null
        );
    }

    @Operation(summary = "나의 예약 조회")
    @GetMapping("/my")
    public BaseResponse<BookingListResponse<BookingDetailResponse>> getMyBookings(
        HttpSession session
    ) {
        Long sessionMemberId = (Long) session.getAttribute("memberId");

        BookingListResponse<BookingDetailResponse> responses = bookingService.getMyBookings(sessionMemberId);
        return BaseResponse.onSuccess("나의 예약 조회 성공", responses);
    }

    @Operation(summary = "시간대별 예약 조회")
    @GetMapping("/timeslot/{timeSlotId}")
    public BaseResponse<BookingListResponse<BookingDetailResponse>> getBookingsByTimeSlot(
        @PathVariable Long timeSlotId
    ) {
        BookingListResponse<BookingDetailResponse> responses = bookingService.getBookingsByTimeSlot(timeSlotId);
        return BaseResponse.onSuccess("시간대별 예약 조회 성공", responses);
    }

    @Operation(summary = "예약 상세 조회")
    @GetMapping("/{bookingId}")
    public BaseResponse<BookingDetailResponse> getBookingDetail(
        @PathVariable Long bookingId
    ) {
        BookingDetailResponse response = bookingService.getBookingDetail(bookingId);
        return BaseResponse.onSuccess("예약 상세 조회 성공", response);
    }
}
