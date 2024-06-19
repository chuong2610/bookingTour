package com.example.bookingtour.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name="wishlists")
@Data
public class WishlistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="added_date")
    private LocalDateTime addedDate;

    @ManyToOne
    @JoinColumn(name="id_user")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name="id_tour")
    private TourEntity tour;
}
