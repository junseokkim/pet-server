package com.kt.pet_server.service;

import com.kt.pet_server.dto.request.booking.BookingRequest;
import com.kt.pet_server.dto.response.booking.BookingDetailResponse;
import com.kt.pet_server.dto.response.booking.BookingListResponse;
import com.kt.pet_server.dto.response.booking.BookingResponse;
import java.util.List;

public interface BookingService {
    BookingResponse createBooking(Long memberId, BookingRequest request);
    void cancelBooking(Long memberId, Long bookingId);
    BookingListResponse<BookingDetailResponse> getMyBookings(Long memberId);
    BookingListResponse<BookingDetailResponse> getBookingsByTimeSlot(Long timeSlotId);
    BookingDetailResponse getBookingDetail(Long bookingId);
}



