package com.example.bookingtour.service.imp;

import com.example.bookingtour.dto.Admin_BookingDTO;
import com.example.bookingtour.payload.request.InsertBookingRequest;

import java.util.List;

public interface BookingServiceImp {

    boolean insertBooking(InsertBookingRequest request);

    boolean updateBookingById(InsertBookingRequest request, int id);

    List<Admin_BookingDTO> getAllBookingByAdmin();

}
