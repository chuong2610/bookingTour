package com.example.bookingtour.dto;

import lombok.Data;

import java.util.List;

@Data
public class Admin_LocationDTO {
    private int idLocation;
    private String nameLocation;
    private String address;
    private String city;
    private String country;
    private List<String> listImage;

}
