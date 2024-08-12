package com.example.bookingtour.service.imp;

import com.example.bookingtour.dto.Admin_UserDTO;
import com.example.bookingtour.entity.UserEntity;
import com.example.bookingtour.payload.request.InsertUserRequest;

import java.util.List;

public interface UserServiceImp {
    boolean insertNewUser(InsertUserRequest insertUserRequest);

    boolean updateUserById(InsertUserRequest insertUserRequest, int id);

    List<Admin_UserDTO> getAllForAdmin();

    List<Admin_UserDTO> searchUsers(String keyword);
}
