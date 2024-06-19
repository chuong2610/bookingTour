package com.example.bookingtour.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name="image_tour")
@Data
public class ImageTourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="image")
    private String image;
    @ManyToOne
    @JoinColumn(name="id_tour")
    private TourEntity tour;
}
