package com.example.bookingtour.payload.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InsertPaymentRequest {
    private int idBooking;
    private String paymentMethod;
}
