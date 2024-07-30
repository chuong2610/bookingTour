package com.example.bookingtour.dto;

import com.example.bookingtour.entity.RoleEntity;
import lombok.Data;

@Data
public class Admin_UserDTO {
    private int idUser;
    private String fullName;
    private String email;
    private String avatar;
    private String phoneNumber;
    private RoleEntity roleUser;
}
