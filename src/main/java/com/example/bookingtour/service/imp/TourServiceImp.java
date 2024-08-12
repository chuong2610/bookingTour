package com.example.bookingtour.service.imp;

import com.example.bookingtour.dto.Admin_TourDTO;
import com.example.bookingtour.payload.request.TourRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface TourServiceImp {
    boolean insertTour(TourRequest tourRequest);
    boolean updateTour(TourRequest request, int id);
    List<Admin_TourDTO> getAllTourByAdmin();
    List<Admin_TourDTO> searchTours(String keyword);

}
