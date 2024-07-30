package com.example.bookingtour.service;

import com.example.bookingtour.dto.Admin_LocationDTO;
import com.example.bookingtour.entity.ImageLocationEntity;
import com.example.bookingtour.entity.LocationEntity;
import com.example.bookingtour.exception.ContactFormNotFoundException;
import com.example.bookingtour.exception.InsertLocationException;
import com.example.bookingtour.exception.ObjectNotFoundException;
import com.example.bookingtour.payload.request.InsertLocationRequest;
import com.example.bookingtour.repository.LocationImageRepository;
import com.example.bookingtour.repository.LocationRepository;
import com.example.bookingtour.service.imp.FileServiceImp;
import com.example.bookingtour.service.imp.LocationServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService implements LocationServiceImp {

    @Autowired
    private FileServiceImp fileServiceImp;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationImageRepository locationImageRepository;

    @Transactional
    @Override
    public boolean insertLocation(InsertLocationRequest request) {
        boolean issSuccess = false;
        try{
            // Set cac thong tin con lai
            LocationEntity locationEntity = new LocationEntity();
            locationEntity.setName(request.getName());
            locationEntity.setAddress(request.getAddress());
            locationEntity.setCity(request.getCity());
            locationEntity.setCountry(request.getCountry());
            // Save vo bang location --> lay id location vua them
            LocationEntity locationSaved = locationRepository.save(locationEntity);
            // Save vo bang image_location
            for(MultipartFile file : request.getFile()){
                // Luu file
                boolean isCopySuccess = fileServiceImp.saveFile(file);
                if(isCopySuccess){
                    // Tao va luu ImageLocationEntity cho moi file
                    ImageLocationEntity imageLocationEntity = new ImageLocationEntity();
                    imageLocationEntity.setLocation(locationSaved);
                    imageLocationEntity.setImage(file.getOriginalFilename());
                    locationImageRepository.save(imageLocationEntity);
                } else {
                    throw new InsertLocationException("Lỗi không thể thêm file: " + file.getOriginalFilename());
                }
            }

            issSuccess = true;

        } catch(Exception e){
            throw new InsertLocationException();
        }
        return issSuccess;
    }

    @Override
    public boolean deleteLocationById(int id) {
        boolean isSuccess = false;
        if(locationRepository.existsById(id)){
            int idLocation = id;
            if(locationImageRepository.existsImageLocationEntityByLocation_Id(idLocation))
            {
                locationImageRepository.deleteByLocation_Id(idLocation);
            }
            locationRepository.deleteById(idLocation);
            isSuccess = true;

        } else {
            throw new ContactFormNotFoundException("Không tìm thấy form location với id = " + id);
        }
        return isSuccess;
    }

    @Override
    public boolean updateLocationById(InsertLocationRequest insertLocationRequest, int id) {
        boolean isSuccess = false;
        try{
            LocationEntity locationSaved = locationRepository.findById(id);
            if(locationSaved != null){
                locationSaved.setName(insertLocationRequest.getName());
                locationSaved.setCountry(insertLocationRequest.getCountry());
                locationSaved.setCity(insertLocationRequest.getCity());
                locationSaved.setAddress(insertLocationRequest.getAddress());

                // Xoa cac ImageLocationEntity neu can thiet
                List<ImageLocationEntity> oldImages = locationImageRepository.findAllByLocation_Id(id);
                locationImageRepository.deleteAll(oldImages);

                for(MultipartFile file : insertLocationRequest.getFile()){
                    boolean isCopySuccess = fileServiceImp.saveFile(file);
                    if(isCopySuccess){
                        ImageLocationEntity imageLocationEntity = new ImageLocationEntity();
                        imageLocationEntity.setImage(file.getOriginalFilename());
                        imageLocationEntity.setLocation(locationSaved);
                        locationImageRepository.save(imageLocationEntity);
                    } else{
                        throw new InsertLocationException("Không thể lưu file: " + file.getOriginalFilename());
                    }
                }

                locationRepository.save(locationSaved);
                isSuccess = true;
            }
        }catch(Exception e){
            isSuccess = false;
            throw new ContactFormNotFoundException("Không tìm thấy location với id = " + id);
        }

        return isSuccess;
    }

    @Override
    public List<Admin_LocationDTO> getAllLocationByAdmin() {
        List<Admin_LocationDTO> locationDTOList = new ArrayList<>();
        List<LocationEntity> locationEntityList = locationRepository.findAll();
        if(locationEntityList.isEmpty()){
            throw new ObjectNotFoundException("Không tìm thấy bất kỳ thông tin Location nào");
        }

        locationEntityList.forEach(item -> {
            Admin_LocationDTO locationDTO = new Admin_LocationDTO();
            locationDTO.setIdLocation(item.getId());
            locationDTO.setNameLocation(item.getName());
            locationDTO.setAddress(item.getAddress());
            locationDTO.setCity(item.getCity());
            locationDTO.setCountry(item.getCountry());

            List<String> listImage = new ArrayList<>();
            item.getListImage().forEach(image ->{
                listImage.add("http://localhost:8080/file/" + image.getImage());
            });
            locationDTO.setListImage(listImage);

            locationDTOList.add(locationDTO);
        });

        return locationDTOList;
    }
}
