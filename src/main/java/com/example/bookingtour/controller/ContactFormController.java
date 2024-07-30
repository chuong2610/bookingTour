package com.example.bookingtour.controller;

import com.example.bookingtour.dto.Admin_ContactFormDTO;
import com.example.bookingtour.dto.Admin_UserDTO;
import com.example.bookingtour.exception.ContactFormNotFoundException;
import com.example.bookingtour.payload.request.InsertContactFormRequest;
import com.example.bookingtour.payload.response.BaseResponse;
import com.example.bookingtour.service.imp.ContactFormServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactFormController {

    @Autowired
    private ContactFormServiceImp contactFormServiceImp;

    @PostMapping("")
    public ResponseEntity<?> insertContactForm(InsertContactFormRequest request){

        boolean isSuccess = contactFormServiceImp.insertContactForm(request);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage(isSuccess ? "Thêm mới contact thành công!" : "Thêm mới contact thất bại!");
        baseResponse.setData(isSuccess);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContactFormById(@PathVariable int id){

        boolean isSuccess = contactFormServiceImp.deleteContactFormById(id);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage(isSuccess ? "Xóa contact thành công!" : "Xóa contact thất bại!");
        baseResponse.setData(isSuccess);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContactFormById(InsertContactFormRequest request, @PathVariable int id){
        boolean isSuccess = contactFormServiceImp.updateContactFormById(request, id);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage(isSuccess ? "Update contact thành công!" : "Update contact thất bại!");
        baseResponse.setData(isSuccess);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllContactFormForAdmin(){
        List<Admin_ContactFormDTO> list = contactFormServiceImp.getAllContactFormByAdmin();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setData(list);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}
