package com.example.bookingtour.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name="blog_posts")
@Data
public class BlogPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="title")
    private String title;
    @Column(name="content")
    private String content;
    @Column(name="published_date")
    private LocalDateTime publishedDate;
    @Column(name="status")
    private String status;

    @ManyToOne
    @JoinColumn(name="id_user")
    private UserEntity user;
}
