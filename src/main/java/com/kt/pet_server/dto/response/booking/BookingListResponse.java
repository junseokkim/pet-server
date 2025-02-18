package com.kt.pet_server.dto.response.booking;

import java.util.List;

public record BookingListResponse <T> (
    List<T> bookingList
) {
    public static <T> BookingListResponse<T> from(List<T> bookingList) {
        return new BookingListResponse<>(bookingList);
    }
}
