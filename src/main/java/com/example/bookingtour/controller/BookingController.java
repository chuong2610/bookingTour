package com.example.bookingtour.controller;

import com.example.bookingtour.dto.Admin_BookingDTO;
import com.example.bookingtour.dto.Admin_TourGuideDTO;
import com.example.bookingtour.payload.request.InsertBookingRequest;
import com.example.bookingtour.payload.request.TourGuideRequest;
import com.example.bookingtour.payload.response.BaseResponse;
import com.example.bookingtour.service.imp.BookingServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingServiceImp bookingServiceImp;

    @PostMapping("")
    public ResponseEntity<?> insertBooking(InsertBookingRequest request){
        boolean isSuccess = bookingServiceImp.insertBooking(request);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage(isSuccess ? "Booking thành công!" : "Booking thất bại!");
        baseResponse.setData(isSuccess);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBookingById(InsertBookingRequest request, @PathVariable int id){
        boolean isSuccess = bookingServiceImp.updateBookingById(request, id);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage(isSuccess ? "Update booking thành công!" : "Update booking thất bại!");
        baseResponse.setData(isSuccess);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllBookingForAdmin(){
        List<Admin_BookingDTO> list = bookingServiceImp.getAllBookingByAdmin();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setData(list);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}
