package com.example.bookingtour.payload.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class TourRequest {
    private MultipartFile[] file;
    private String name;
    private String description;
    private String duration;
    private int maxParticipants;
    private int currentParticipants;
    private double price;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int idLocation;
    private int idTourGuide;
    private String status;
}
