package com.example.bookingtour.payload.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class InsertLocationRequest {
    private MultipartFile[] file;
    private String name;
    private String address;
    private String city;
    private String country;
}
