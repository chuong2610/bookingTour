package com.example.bookingtour.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name="contact_form")
@Data
public class ContactFormEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="firstname")
    private String firstName;
    @Column(name="lastname")
    private String lastName;
    @Column(name="email")
    private String email;
    @Column(name="phone_number")
    private String phone;
    @Column(name="message")
    private String message;
}
