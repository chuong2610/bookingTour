package com.example.bookingtour.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name="users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="avt")
    private String avt;
    @Column(name="phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name="id_role")
    private RoleEntity role;

    @OneToMany(mappedBy = "user")
    private List<BookingEntity> listBooking;

    @OneToMany(mappedBy = "user")
    private List<BlogPostEntity> listBlogPost;

    @OneToMany(mappedBy = "user")
    private List<ReviewEntity> listReview;

    @OneToMany(mappedBy = "user")
    private List<WishlistEntity> wishlist;

}
