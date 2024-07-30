package com.example.bookingtour.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name="payments")
@Data
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="amount")
    private double amount;
    @Column(name="payment_date")
    private LocalDateTime paymentDate;
    @Column(name="payment_method")
    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name="id_booking")
    private BookingEntity booking;

}
