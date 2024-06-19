package com.example.bookingtour.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name="image_location")
@Data
public class ImageLocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="image")
    private String image;
    @ManyToOne
    @JoinColumn(name="id_location")
    private LocationEntity location;

}
