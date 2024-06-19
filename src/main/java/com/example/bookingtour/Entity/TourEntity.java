package com.example.bookingtour.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name="tours")
@Data
public class TourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="fullname")
    private String fullName;
    @Column(name="description")
    private String description;
    @Column(name="duration")
    private String duration;
    @Column(name="max_participant")
    private int maxParticipants;
    @Column(name="current_participant")
    private int currentParticipant;
    @Column(name="price")
    private double price;
    @Column(name="start_date")
    private LocalDateTime startDate;
    @Column(name="end_date")
    private LocalDateTime endDate;
    @Column(name="status")
    private String status;



    @ManyToOne
    @JoinColumn(name="id_location")
    private LocationEntity location;

    @ManyToOne
    @JoinColumn(name="id_tour_guide")
    private TourGuideEntity tourGuide;

    @OneToMany(mappedBy = "tour")
    private List<ImageTourEntity>listImage;

    @OneToMany(mappedBy = "tour")
    private List<BookingEntity> booking;

    @OneToMany(mappedBy = "tour")
    private List<WishlistEntity> wishlist;

    @OneToMany(mappedBy = "tour")
    private List<ReviewEntity> listReview;

}
