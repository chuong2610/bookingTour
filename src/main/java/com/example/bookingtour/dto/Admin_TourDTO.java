package com.example.bookingtour.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Admin_TourDTO {
    private int idTour;
    private List<String> listImage;
    private String nameTour;
    private String descriptionTour;
    private String durationTour;
    private int maxParticipants;
    private int currentParticipants;
    private double priceTour;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int idLocation;
    private String locationName;
    private String locationAddress;
    private String locationCity;
    private String locationCountry;
    private int idTourGuide;
    private String tourGuideName;
    private String tourGuideLanguage;
    private String tourGuideEmail;
    private String tourGuidePhone;
    private String tourGuideExp;
    private String tourGuideBio;
    private String statusTour;
}
