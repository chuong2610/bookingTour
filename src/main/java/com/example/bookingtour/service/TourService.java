package com.example.bookingtour.service;

import com.example.bookingtour.dto.Admin_TourDTO;
import com.example.bookingtour.entity.*;
import com.example.bookingtour.exception.InsertTourException;
import com.example.bookingtour.exception.ObjectNotFoundException;
import com.example.bookingtour.payload.request.TourRequest;
import com.example.bookingtour.repository.LocationRepository;
import com.example.bookingtour.repository.TourGuideRepository;
import com.example.bookingtour.repository.TourImageRepository;
import com.example.bookingtour.repository.TourRepository;
import com.example.bookingtour.service.imp.FileServiceImp;
import com.example.bookingtour.service.imp.TourServiceImp;
import com.example.bookingtour.utils.DateTimeUtil;
import com.example.bookingtour.utils.TourSpecification;
import com.example.bookingtour.utils.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TourService implements TourServiceImp {
    @Autowired
    private FileServiceImp fileServiceImp;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourImageRepository tourImageRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private TourGuideRepository tourGuideRepository;

    @Transactional
    @Override
    public boolean insertTour(TourRequest tourRequest) {
        boolean isSuccess = false;
        LocationEntity locationEntitySaved = locationRepository.findById(tourRequest.getIdLocation());
        TourGuideEntity tourGuideEntitySaved = tourGuideRepository.findById(tourRequest.getIdTourGuide());

        if(locationEntitySaved == null ){
            throw new ObjectNotFoundException("Không tìm thấy location với id = " + tourRequest.getIdLocation());
        }
        if(tourGuideEntitySaved == null){
            throw new ObjectNotFoundException("Không tìm thấy tour guide với id = " + tourRequest.getIdTourGuide());
        }
        try{
            if(tourRequest.getMaxParticipants() > 0 && tourRequest.getPrice() > 0){
                TourEntity tourEntity = new TourEntity();
                tourEntity.setName(tourRequest.getName());
                tourEntity.setDescription(tourRequest.getDescription());
                tourEntity.setDuration(tourRequest.getDuration());
                tourEntity.setMaxParticipant(tourRequest.getMaxParticipants());
                tourEntity.setCurrentParticipant(0);
                tourEntity.setPrice(tourRequest.getPrice());
                tourEntity.setLocation(locationEntitySaved);
                tourEntity.setTourGuide(tourGuideEntitySaved);

                // Convert String sang LocalDateTime
                String pattern = "dd-MM-yyyy HH:mm";
                LocalDateTime startDateString = DateTimeUtil.convertStringToLocalDateTime(tourRequest.getStartDate(), pattern);
                LocalDateTime endDateString = DateTimeUtil.convertStringToLocalDateTime(tourRequest.getEndDate(), pattern);

                if(startDateString.isAfter(LocalDateTime.now()) && endDateString.isAfter(startDateString)){
                    tourEntity.setStartDate(startDateString);
                    tourEntity.setEndDate(endDateString);
                    tourEntity.setStatus("Còn trống");

                    // Luu vo bang tours
                    TourEntity tourSaved = tourRepository.save(tourEntity);

                    // Save bang image tour, luu vo bang image tour
                    for(MultipartFile file : tourRequest.getFile()){
                        boolean isCopySuccess = fileServiceImp.saveFile(file);
                        if(isCopySuccess){
                            ImageTourEntity imageTourEntity = new ImageTourEntity();
                            imageTourEntity.setImage(file.getOriginalFilename());
                            imageTourEntity.setTour(tourSaved);
                            tourImageRepository.save(imageTourEntity);
                        } else {
                            throw new InsertTourException("Không thể save file: " + file.getOriginalFilename());
                        }

                    }

                    isSuccess = true;
                }

            }
        }catch(Exception e){
            throw new InsertTourException(e.getMessage());
        }
        return isSuccess;
    }

    @Transactional
    @Override
    public boolean updateTour(TourRequest request, int id) {
        boolean isSuccess = false;
        LocationEntity locationEntitySaved = locationRepository.findById(request.getIdLocation());
        TourGuideEntity tourGuideEntitySaved = tourGuideRepository.findById(request.getIdTourGuide());
        TourEntity tourSaved = tourRepository.findById(id);

        if(tourSaved == null ){
            throw new ObjectNotFoundException("Không tìm thấy tour với id = " + id);
        }
        if(locationEntitySaved == null ){
            throw new ObjectNotFoundException("Không tìm thấy location với id = " + request.getIdLocation());
        }
        if(tourGuideEntitySaved == null){
            throw new ObjectNotFoundException("Không tìm thấy tour guide với id = " + request.getIdTourGuide());
        }

        try{
            if(request.getMaxParticipants() > 0 && request.getPrice() > 0){
                tourSaved.setName(request.getName());
                tourSaved.setDescription(request.getDescription());
                tourSaved.setDuration(request.getDuration());
                tourSaved.setMaxParticipant(request.getMaxParticipants());
                tourSaved.setCurrentParticipant(0);
                tourSaved.setPrice(request.getPrice());
                tourSaved.setLocation(locationEntitySaved);
                tourSaved.setTourGuide(tourGuideEntitySaved);

                // Convert String sang LocalDateTime
                String pattern = "dd-MM-yyyy HH:mm";
                LocalDateTime startDateString = DateTimeUtil.convertStringToLocalDateTime(request.getStartDate(), pattern);
                LocalDateTime endDateString = DateTimeUtil.convertStringToLocalDateTime(request.getEndDate(), pattern);

                if(startDateString.isAfter(LocalDateTime.now()) && endDateString.isAfter(startDateString)){
                    tourSaved.setStartDate(startDateString);
                    tourSaved.setEndDate(endDateString);
                    tourSaved.setStatus(request.getStatus());

                    // Luu vo bang tours
                    tourRepository.save(tourSaved);

                    // Xoa cac ImageTourEntity neu can thiet
                    List<ImageTourEntity> oldImages = tourImageRepository.findAllByTour_Id(id);
                    tourImageRepository.deleteAll(oldImages);

                    for(MultipartFile file : request.getFile()){
                        boolean isCopySuccess = fileServiceImp.saveFile(file);
                        if(isCopySuccess){
                            // Save bang image tour, luu vo bang image tour
                            ImageTourEntity imageTourEntity = new ImageTourEntity();
                            imageTourEntity.setImage(file.getOriginalFilename());
                            imageTourEntity.setTour(tourSaved);
                            tourImageRepository.save(imageTourEntity);
                        } else {
                            throw new InsertTourException("Không thể save file: " + file.getOriginalFilename());
                        }
                    }
                    isSuccess = true;
                }
            }
        }catch(Exception e){
            throw new InsertTourException(e.getMessage());
        }
        return isSuccess;
    }

    @Override
    public List<Admin_TourDTO> getAllTourByAdmin() {
        List<Admin_TourDTO> tourDTOList = new ArrayList<>();

        List<TourEntity> tourEntityList = tourRepository.findAll();
        if (tourEntityList.isEmpty()) {
            throw new ObjectNotFoundException("Không tìm thấy bất kỳ thông tin Tour nào");
        }

        tourEntityList.forEach(item -> {
            Admin_TourDTO tourDTO = new Admin_TourDTO();
            tourDTO.setIdTour(item.getId());

            List<String> listImage = new ArrayList<>();
            item.getListImage().forEach(image -> {
                listImage.add("http://localhost:8080/file/" + image.getImage());
            });
            tourDTO.setListImage(listImage);

            tourDTO.setNameTour(item.getName());
            tourDTO.setDescriptionTour(item.getDescription());
            tourDTO.setDurationTour(item.getDuration());
            tourDTO.setMaxParticipants(item.getMaxParticipant());
            tourDTO.setCurrentParticipants(item.getCurrentParticipant());
            tourDTO.setPriceTour(item.getPrice());
            tourDTO.setStartDate(item.getStartDate());
            tourDTO.setEndDate(item.getEndDate());

            // Lấy thông tin LocationEntity
            LocationEntity locationEntity = locationRepository.findById(item.getLocation().getId());
            tourDTO.setIdLocation(locationEntity.getId());
            tourDTO.setLocationName(locationEntity.getName());
            tourDTO.setLocationAddress(locationEntity.getAddress());
            tourDTO.setLocationCity(locationEntity.getCity());
            tourDTO.setLocationCountry(locationEntity.getCountry());

            // Lấy thông tin TourGuideEntity
            TourGuideEntity tourGuideEntity = tourGuideRepository.findById(item.getTourGuide().getId());
            tourDTO.setIdTourGuide(tourGuideEntity.getId());
            tourDTO.setTourGuideName(tourGuideEntity.getFullName());
            tourDTO.setTourGuideLanguage(tourGuideEntity.getLanguage());
            tourDTO.setTourGuideEmail(tourGuideEntity.getEmail());
            tourDTO.setTourGuidePhone(tourGuideEntity.getPhone());
            tourDTO.setTourGuideExp(tourGuideEntity.getExperience());
            tourDTO.setTourGuideBio(tourGuideEntity.getBio());

            tourDTO.setStatusTour(item.getStatus());

            tourDTOList.add(tourDTO);
        });

        return tourDTOList;
    }

    @Override
    public List<Admin_TourDTO> searchTours(String keyword) {
        List<Admin_TourDTO> tourDTOList = new ArrayList<>();
        Specification<TourEntity> specification = TourSpecification.containsKeyword(keyword);

        List<TourEntity> tourEntityList = tourRepository.findAll(specification);

        if (tourEntityList.isEmpty()){
            throw new ObjectNotFoundException("Không tìm thấy thông tin");
        }

        tourEntityList.forEach(item -> {
            Admin_TourDTO tourDTO = new Admin_TourDTO();
            tourDTO.setIdTour(item.getId());

            List<String> listImage = new ArrayList<>();
            item.getListImage().forEach(image -> {
                listImage.add("http://localhost:8080/file/" + image.getImage());
            });
            tourDTO.setListImage(listImage);

            tourDTO.setNameTour(item.getName());
            tourDTO.setDescriptionTour(item.getDescription());
            tourDTO.setDurationTour(item.getDuration());
            tourDTO.setMaxParticipants(item.getMaxParticipant());
            tourDTO.setCurrentParticipants(item.getCurrentParticipant());
            tourDTO.setPriceTour(item.getPrice());
            tourDTO.setStartDate(item.getStartDate());
            tourDTO.setEndDate(item.getEndDate());

            // Lấy thông tin LocationEntity
            LocationEntity locationEntity = locationRepository.findById(item.getLocation().getId());
            tourDTO.setIdLocation(locationEntity.getId());
            tourDTO.setLocationName(locationEntity.getName());
            tourDTO.setLocationAddress(locationEntity.getAddress());
            tourDTO.setLocationCity(locationEntity.getCity());
            tourDTO.setLocationCountry(locationEntity.getCountry());

            // Lấy thông tin TourGuideEntity
            TourGuideEntity tourGuideEntity = tourGuideRepository.findById(item.getTourGuide().getId());
            tourDTO.setIdTourGuide(tourGuideEntity.getId());
            tourDTO.setTourGuideName(tourGuideEntity.getFullName());
            tourDTO.setTourGuideLanguage(tourGuideEntity.getLanguage());
            tourDTO.setTourGuideEmail(tourGuideEntity.getEmail());
            tourDTO.setTourGuidePhone(tourGuideEntity.getPhone());
            tourDTO.setTourGuideExp(tourGuideEntity.getExperience());
            tourDTO.setTourGuideBio(tourGuideEntity.getBio());

            tourDTO.setStatusTour(item.getStatus());

            tourDTOList.add(tourDTO);
        });

        return tourDTOList;
    }

}
