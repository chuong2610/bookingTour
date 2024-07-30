package com.example.bookingtour.service.imp;

import com.example.bookingtour.dto.Admin_ContactFormDTO;
import com.example.bookingtour.payload.request.InsertContactFormRequest;

import java.util.List;

public interface ContactFormServiceImp {
    boolean insertContactForm(InsertContactFormRequest insertContactFormRequest);
    boolean deleteContactFormById(int id);
    boolean updateContactFormById(InsertContactFormRequest insertContactFormRequest, int id);

    List<Admin_ContactFormDTO> getAllContactFormByAdmin();

}
