package com.example.bookingtour.controller;

import com.example.bookingtour.dto.Admin_TourDTO;
import com.example.bookingtour.dto.Admin_UserDTO;
import com.example.bookingtour.payload.request.TourGuideRequest;
import com.example.bookingtour.payload.request.TourRequest;
import com.example.bookingtour.payload.response.BaseResponse;
import com.example.bookingtour.service.imp.TourServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tour")
public class TourController {
    @Autowired
    private TourServiceImp tourServiceImp;

    @PostMapping("")
    public ResponseEntity<?> insertTour(TourRequest tourRequest){
        boolean isSuccess = tourServiceImp.insertTour(tourRequest);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage(isSuccess ? "Thêm tour thành công!" : "Thêm tour thất bại!");
        baseResponse.setData(isSuccess);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTourById(TourRequest request, @PathVariable int id){
        boolean isSuccess = tourServiceImp.updateTour(request, id);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage(isSuccess ? "Update tour thành công!" : "Update tour thất bại!");
        baseResponse.setData(isSuccess);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllTourForAdmin(){
        List<Admin_TourDTO> list = tourServiceImp.getAllTourByAdmin();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setData(list);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchTours(@RequestParam(required = false) String keyword)
    {
        List<Admin_TourDTO> list = tourServiceImp.searchTours(keyword);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setData(list);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
