package com.example.bookingtour.service.imp;

import com.example.bookingtour.dto.Admin_PaymentDTO;
import com.example.bookingtour.payload.request.InsertPaymentRequest;

import java.util.List;

public interface PaymentServiceImp {
    boolean insertPayment(InsertPaymentRequest insertPaymentRequest);

    List<Admin_PaymentDTO> getAllPaymentByAdmin();

}
