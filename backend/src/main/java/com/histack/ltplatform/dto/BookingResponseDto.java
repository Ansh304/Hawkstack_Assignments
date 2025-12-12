package com.histack.ltplatform.dto;

import java.util.List;

public class BookingResponseDto {
    private List<BookingDto> bookings;
    private long totalBookings;

    public BookingResponseDto() {}

    public BookingResponseDto(List<BookingDto> bookings, long totalBookings) {
        this.bookings = bookings;
        this.totalBookings = totalBookings;
    }

    // Getters & Setters
    public List<BookingDto> getBookings() { return bookings; }
    public void setBookings(List<BookingDto> bookings) { this.bookings = bookings; }
    
    public long getTotalBookings() { return totalBookings; }
    public void setTotalBookings(long totalBookings) { this.totalBookings = totalBookings; }
}
