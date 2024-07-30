package com.example.bookingtour.service.imp;

import com.example.bookingtour.dto.Admin_TourGuideDTO;
import com.example.bookingtour.payload.request.TourGuideRequest;

import java.util.List;

public interface TourGuideServiceImp {
    boolean insertTourGuide(TourGuideRequest tourGuideRequest);
    boolean updateTourGuideById(TourGuideRequest tourGuideRequest, int id);

    List<Admin_TourGuideDTO> getAllByAdmin();
}
