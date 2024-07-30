package com.example.bookingtour.dto;

import lombok.Data;

@Data
public class Admin_TourGuideDTO {
    private int idTourGuide;
    private String fullName;
    private String language;
    private String email;
    private String phoneNumber;
    private String experience;
    private String bio;
}
