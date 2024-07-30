package com.example.bookingtour.payload.request;

import lombok.Data;

@Data
public class InsertContactFormRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String phone_number;
    private String message;
}
