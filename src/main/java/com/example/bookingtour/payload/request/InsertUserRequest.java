package com.example.bookingtour.payload.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class InsertUserRequest {
    private String fullname;
    private String email;
    private String password;
    private MultipartFile avt;
    private String phone;
    private int idRole;
}
