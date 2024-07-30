package com.example.bookingtour.payload.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InsertBookingRequest {
    private int idTour;
    private int idUser;
    private int numOfPeople;
    private LocalDateTime bookingDate;
    private double totalPrice;
    private String status;
}
