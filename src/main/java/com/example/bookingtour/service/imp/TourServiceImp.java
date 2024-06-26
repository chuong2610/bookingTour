package com.example.bookingtour.service.imp;

import com.example.bookingtour.payload.request.TourRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface TourServiceImp {
    boolean insertTour(HttpServletRequest request, TourRequest tourRequest);

}
