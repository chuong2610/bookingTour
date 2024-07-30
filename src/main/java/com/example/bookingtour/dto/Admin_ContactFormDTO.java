package com.example.bookingtour.dto;

import lombok.Data;

@Data
public class Admin_ContactFormDTO {
    private int idContactForm;
    private String firstName;
    private String lastName;
    private String emailUser;
    private String phoneNumber;
    private String message;
}
