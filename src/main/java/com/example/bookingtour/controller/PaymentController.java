package com.example.bookingtour.controller;

import com.example.bookingtour.dto.Admin_PaymentDTO;
import com.example.bookingtour.payload.request.InsertPaymentRequest;
import com.example.bookingtour.payload.response.BaseResponse;
import com.example.bookingtour.service.imp.PaymentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentServiceImp paymentServiceImp;

    @PostMapping("")
    public ResponseEntity<?> insertPayment(InsertPaymentRequest insertPaymentRequest){
        boolean isSuccess = paymentServiceImp.insertPayment(insertPaymentRequest);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage(isSuccess ? "Thêm payment thành công!" : "Thêm payment thất bại!");
        baseResponse.setData(isSuccess);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllPaymentForAdmin(){
        List<Admin_PaymentDTO> list = paymentServiceImp.getAllPaymentByAdmin();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setData(list);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
