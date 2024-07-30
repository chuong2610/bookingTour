package com.example.bookingtour.service;

import com.example.bookingtour.dto.Admin_TourGuideDTO;
import com.example.bookingtour.entity.TourGuideEntity;
import com.example.bookingtour.exception.InsertTourGuideException;
import com.example.bookingtour.exception.ObjectNotFoundException;
import com.example.bookingtour.payload.request.TourGuideRequest;
import com.example.bookingtour.repository.TourGuideRepository;
import com.example.bookingtour.service.imp.TourGuideServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TourGuideService implements TourGuideServiceImp {

    @Autowired
    private TourGuideRepository tourGuideRepository;

    @Override
    public boolean insertTourGuide(TourGuideRequest request) {
        boolean isSuccess = false;

        try{
            TourGuideEntity tourGuideEntity = new TourGuideEntity();
            tourGuideEntity.setFullName(request.getFullname());
            tourGuideEntity.setLanguage(request.getLanguage());
            tourGuideEntity.setEmail(request.getEmail());
            tourGuideEntity.setPhone(request.getPhone());
            tourGuideEntity.setBio(request.getBio());
            tourGuideEntity.setExperience(request.getExperience());

            tourGuideRepository.save(tourGuideEntity);

            isSuccess = true;

        }catch (Exception e){
            throw new InsertTourGuideException();
        }

        return isSuccess;
    }

    @Override
    public boolean updateTourGuideById(TourGuideRequest request, int id) {
        boolean isSuccess = false;
        TourGuideEntity tourGuideSaved = tourGuideRepository.findById(id);
        try{
            if (tourGuideSaved!= null){
                tourGuideSaved.setFullName(request.getFullname());
                tourGuideSaved.setLanguage(request.getLanguage());
                tourGuideSaved.setEmail(request.getEmail());
                tourGuideSaved.setPhone(request.getPhone());
                tourGuideSaved.setExperience(request.getExperience());
                tourGuideSaved.setBio(request.getBio());
                tourGuideRepository.save(tourGuideSaved);
                isSuccess = true;
            } else throw new InsertTourGuideException("Không tìm thấy tour guide có id = " + id);
        }catch (Exception e){
            throw new InsertTourGuideException(e.getMessage());
        }
        return isSuccess;
    }

    @Override
    public List<Admin_TourGuideDTO> getAllByAdmin() {
        List<Admin_TourGuideDTO> adminTourGuideDTOList = new ArrayList<>();

        List<TourGuideEntity> tourGuideEntityList = tourGuideRepository.findAll();

        if(tourGuideEntityList.isEmpty()){
            throw new ObjectNotFoundException("Không tìm thấy bất kỳ thông tin Tour Guide nào");
        }

        tourGuideEntityList.forEach(item -> {
            Admin_TourGuideDTO tourGuide = new Admin_TourGuideDTO();
            tourGuide.setIdTourGuide(item.getId());
            tourGuide.setFullName(item.getFullName());
            tourGuide.setLanguage(item.getLanguage());
            tourGuide.setEmail(item.getEmail());
            tourGuide.setPhoneNumber(item.getPhone());
            tourGuide.setExperience(item.getExperience());
            tourGuide.setBio(item.getBio());

            adminTourGuideDTOList.add(tourGuide);
        });

        return adminTourGuideDTOList;
    }
}
