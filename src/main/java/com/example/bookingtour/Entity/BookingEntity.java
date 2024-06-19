package com.example.bookingtour.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name="booking")
@Data
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="number_of_people")
    private int numberOfPeople;
    @Column(name="booking_date")
    private LocalDateTime bookingDate;
    @Column(name="total_price")
    private double totalPrice;
    @Column(name="status")
    private String status;

    @ManyToOne
    @JoinColumn(name="id_tour")
    private TourEntity tour;

    @ManyToOne
    @JoinColumn(name="id_user")
    private UserEntity user;

    @OneToMany(mappedBy = "booking")
    private List<PaymentEntity> listPayment;


}
