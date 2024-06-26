package com.example.bookingtour.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "location")
@Data
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
