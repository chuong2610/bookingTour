package com.example.bookingtour.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Admin_BookingDTO {
    private int idBooking;
    private String tourBooking;
    private String userBooking;
    private String emailUser;
    private int NumOfPeople;
    private LocalDateTime bookingDate;
    private double totalPrice;
    private String status;
}
