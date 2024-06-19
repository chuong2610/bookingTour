package com.example.bookingtour.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "reviews")
@Data
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="content")
    private String content;
    @Column(name="rating")
    private int rating;

    @ManyToOne
    @JoinColumn(name="id_user")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name="id_tour")
    private TourEntity tour;

}
