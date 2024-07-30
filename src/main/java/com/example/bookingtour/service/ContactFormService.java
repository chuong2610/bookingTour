package com.example.bookingtour.service;

import com.example.bookingtour.dto.Admin_ContactFormDTO;
import com.example.bookingtour.entity.ContactFormEntity;
import com.example.bookingtour.exception.ContactFormNotFoundException;
import com.example.bookingtour.exception.InsertContactFormException;
import com.example.bookingtour.exception.ObjectNotFoundException;
import com.example.bookingtour.payload.request.InsertContactFormRequest;
import com.example.bookingtour.repository.ContactFormRepository;
import com.example.bookingtour.service.imp.ContactFormServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactFormService implements ContactFormServiceImp {

    @Autowired
    private ContactFormRepository contactFormRepository;

    @Override
    public boolean insertContactForm(InsertContactFormRequest request) {
        boolean isSucess = false;

        try{
            ContactFormEntity contactFormEntity = new ContactFormEntity();
            contactFormEntity.setFirstName(request.getFirstname());
            contactFormEntity.setLastName(request.getLastname());
            contactFormEntity.setEmail(request.getEmail());
            contactFormEntity.setPhone(request.getPhone_number());
            contactFormEntity.setMessage(request.getMessage());

            contactFormRepository.save(contactFormEntity);

            isSucess = true;
        }catch(Exception e){
            throw new InsertContactFormException(e.getMessage());
        }

        return isSucess;
    }

    @Override
    public boolean deleteContactFormById(int id) {
        boolean isSuccess = false;
        if(contactFormRepository.existsById(id)){
            contactFormRepository.deleteById(id);
            isSuccess = true;
        } else {
            throw new ContactFormNotFoundException("Không tìm thấy form contact với id = " + id);
        }
        return isSuccess;
    }

    @Override
    public boolean updateContactFormById(InsertContactFormRequest request, int id) {
        boolean isSuccess = false;
        ContactFormEntity contactFormSaved = contactFormRepository.findById(id);
        if(contactFormSaved != null){
            contactFormSaved.setFirstName(request.getFirstname());
            contactFormSaved.setLastName(request.getLastname());
            contactFormSaved.setEmail(request.getEmail());
            contactFormSaved.setPhone(request.getPhone_number());
            contactFormSaved.setMessage(request.getMessage());

            contactFormRepository.save(contactFormSaved);
            isSuccess = true;
        } else {
            throw new ContactFormNotFoundException("Không tìm thấy form contact với id = " + id);
        }
        return isSuccess;
    }

    @Override
    public List<Admin_ContactFormDTO> getAllContactFormByAdmin() {
        List<Admin_ContactFormDTO> contactFormDTOList = new ArrayList<>();
        List<ContactFormEntity> contactFormEntityList = contactFormRepository.findAll();
        if(contactFormEntityList.isEmpty()){
            throw new ObjectNotFoundException("Không tìm thấy bất kỳ thông tin Contact Form nào");
        }
        contactFormEntityList.forEach(item -> {
            Admin_ContactFormDTO optionContactForm = new Admin_ContactFormDTO();
            optionContactForm.setIdContactForm(item.getId());
            optionContactForm.setFirstName(item.getFirstName());
            optionContactForm.setLastName(item.getLastName());
            optionContactForm.setEmailUser(item.getEmail());
            optionContactForm.setPhoneNumber(item.getPhone());
            optionContactForm.setMessage(item.getMessage());

            contactFormDTOList.add(optionContactForm);
        });

        return contactFormDTOList;
    }


}
