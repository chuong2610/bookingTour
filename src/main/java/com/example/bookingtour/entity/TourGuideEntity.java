package com.example.bookingtour.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name="tour_guides")
@Data
public class TourGuideEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="fullname")
    private String fullName;
    @Column(name="language")
    private String language;
    @Column(name="email")
    private String email;
    @Column(name="phone")
    private String phone;
    @Column(name="experience")
    private String experience;
    @Column(name="bio")
    private String bio;

    @OneToMany(mappedBy = "tourGuide")
    private List<TourEntity> listTour;

}
