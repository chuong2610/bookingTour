package com.example.bookingtour.controller;

import com.example.bookingtour.dto.Admin_TourGuideDTO;
import com.example.bookingtour.payload.request.TourGuideRequest;
import com.example.bookingtour.payload.response.BaseResponse;
import com.example.bookingtour.service.imp.TourGuideServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tour-guide")
public class TourGuideController {

    @Autowired
    private TourGuideServiceImp tourGuideServiceImp;

    @PostMapping("")
    public ResponseEntity<?> insertTourGuide(TourGuideRequest request){

        boolean isSuccess = tourGuideServiceImp.insertTourGuide(request);

        BaseResponse baseResponse =  new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage(isSuccess ? "Thêm mới tour guide thành công!" : "Thêm mới tour guide thất bại!");
        baseResponse.setData(isSuccess);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTourGuideById(TourGuideRequest request, @PathVariable int id){
        boolean isSuccess = tourGuideServiceImp.updateTourGuideById(request, id);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage(isSuccess ? "Update tour guide thành công!" : "Update tour guide thất bại!");
        baseResponse.setData(isSuccess);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllTourGuideForAdmin(){
        List<Admin_TourGuideDTO> list = tourGuideServiceImp.getAllByAdmin();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setData(list);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
