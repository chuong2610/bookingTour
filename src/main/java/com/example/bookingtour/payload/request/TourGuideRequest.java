package com.example.bookingtour.payload.request;

import lombok.Data;

@Data
public class TourGuideRequest {
    private String fullname;
    private String language;
    private String email;
    private String phone;
    private String experience;
    private String bio;
}
