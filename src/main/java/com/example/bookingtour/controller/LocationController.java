package com.example.bookingtour.controller;

import com.example.bookingtour.dto.Admin_LocationDTO;
import com.example.bookingtour.payload.request.InsertLocationRequest;
import com.example.bookingtour.payload.response.BaseResponse;
import com.example.bookingtour.service.imp.LocationServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationServiceImp locationServiceImp;

    @PostMapping("")
    public ResponseEntity<?> insertLocation(InsertLocationRequest request){
        boolean isSuccess = locationServiceImp.insertLocation(request);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setData(isSuccess);
        baseResponse.setMessage(isSuccess ? "Thêm mới location thành công!" : "Thêm mới location thất bại!");
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocationById(@PathVariable int id){

        boolean isSuccess = locationServiceImp.deleteLocationById(id);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage(isSuccess ? "Xóa location thành công!" : "Xóa location thất bại!");
        baseResponse.setData(isSuccess);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLocationById(InsertLocationRequest request, @PathVariable int id){
        boolean isSuccess = locationServiceImp.updateLocationById(request, id);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage(isSuccess ? "Update location thành công!" : "Update location thất bại!");
        baseResponse.setData(isSuccess);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllLocationForAdmin(){
        List<Admin_LocationDTO> list = locationServiceImp.getAllLocationByAdmin();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setData(list);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}
