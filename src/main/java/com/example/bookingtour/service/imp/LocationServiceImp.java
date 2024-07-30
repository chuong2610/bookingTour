package com.example.bookingtour.service.imp;

import com.example.bookingtour.dto.Admin_LocationDTO;
import com.example.bookingtour.payload.request.InsertLocationRequest;

import java.util.List;

public interface LocationServiceImp {

    boolean insertLocation(InsertLocationRequest insertLocationRequest);

    boolean deleteLocationById(int id);

    boolean updateLocationById(InsertLocationRequest insertLocationRequest, int id);

    List<Admin_LocationDTO> getAllLocationByAdmin();

}
