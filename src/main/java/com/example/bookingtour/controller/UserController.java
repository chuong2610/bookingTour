package com.example.bookingtour.controller;

import com.example.bookingtour.dto.Admin_UserDTO;
import com.example.bookingtour.payload.request.InsertUserRequest;
import com.example.bookingtour.payload.response.BaseResponse;
import com.example.bookingtour.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImp userServiceImp;

    @PostMapping("/add")
    public ResponseEntity<?> insertNewUSer(InsertUserRequest request){
        boolean isSuccess = userServiceImp.insertNewUser(request);
        BaseResponse baseResponse= new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage(isSuccess ? "Thêm mới user thành công!" : "Thêm mới user thất bại!");
        baseResponse.setData(isSuccess);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(InsertUserRequest request, @PathVariable int id){
        boolean isSuccess = userServiceImp.updateUserById(request, id);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage(isSuccess ? "Update user thành công!" : "Update user thất bại!");
        baseResponse.setData(isSuccess);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllUserForAdmin(){
        List<Admin_UserDTO> list = userServiceImp.getAllForAdmin();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setData(list);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}
