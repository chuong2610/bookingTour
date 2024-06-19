package com.example.bookingtour.controller;

import com.example.bookingtour.Entity.TourEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

    @PostMapping("signin")
    public ResponseEntity<?> signIn(){

        TourEntity tour = new TourEntity();


        return new ResponseEntity<>("signIn", HttpStatus.OK);
    }
}
