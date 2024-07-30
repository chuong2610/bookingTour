package com.example.bookingtour.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Admin_PaymentDTO {
    private int idPayment;
    private Admin_BookingDTO booking;
    private double amount;
    private LocalDateTime paymentDate;
    private String paymentMethod;
}
