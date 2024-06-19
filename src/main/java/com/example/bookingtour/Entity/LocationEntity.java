package com.example.bookingtour.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "location")
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="address")
    private String address;
    @Column(name="city")
    private String city;
    @Column(name="country")
    private String country;
    @OneToMany(mappedBy = "location")
    private List<ImageLocationEntity> listImage;
    @OneToMany(mappedBy = "location")
    private List<TourEntity> listTour;
}
