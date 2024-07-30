package com.example.bookingtour.service;

import com.example.bookingtour.dto.Admin_BookingDTO;
import com.example.bookingtour.entity.BookingEntity;
import com.example.bookingtour.entity.TourEntity;
import com.example.bookingtour.entity.UserEntity;
import com.example.bookingtour.exception.InsertBookingException;
import com.example.bookingtour.exception.ObjectNotFoundException;
import com.example.bookingtour.payload.request.InsertBookingRequest;
import com.example.bookingtour.repository.BookingRepository;
import com.example.bookingtour.repository.TourRepository;
import com.example.bookingtour.repository.UserRepository;
import com.example.bookingtour.service.imp.BookingServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService implements BookingServiceImp {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public boolean insertBooking(InsertBookingRequest request) {
        boolean isSuccess = false;
        try{
            // Check idTour co ton tai?
            if(tourRepository.existsTourEntityById(request.getIdTour())){
                // idUser nữa sẽ lấy từ token sau
                if(userRepository.existsUserEntityById(request.getIdUser())){
                    // check num of people hop le khong?
                    TourEntity tourEntitySaved = tourRepository.findById(request.getIdTour());
                    UserEntity userEntitySaved = userRepository.findById(request.getIdUser());

                    int oldQuantityParticipant = tourEntitySaved.getCurrentParticipant();
                    int newQuantityParticipant = request.getNumOfPeople();
                    int maxQuantityParticipant = tourEntitySaved.getMaxParticipant();

                    if(oldQuantityParticipant + newQuantityParticipant <= maxQuantityParticipant && newQuantityParticipant > 0){
                        BookingEntity booking = new BookingEntity();
                        booking.setTour(tourEntitySaved);
                        booking.setUser(userEntitySaved);
                        booking.setNumberOfPeople(newQuantityParticipant);
                        booking.setBookingDate(LocalDateTime.now());
                        double totalBookingPrice = tourEntitySaved.getPrice() * newQuantityParticipant;
                        booking.setTotalPrice(totalBookingPrice);
                        booking.setStatus("Chưa thanh toán");

                        // Luu vo bang bookings
                        bookingRepository.save(booking);

                        // Cập nhật lại currentParticipant và status trong tours
                        if(oldQuantityParticipant + newQuantityParticipant == maxQuantityParticipant){
                            tourEntitySaved.setStatus("Hết vé");
                        }
                        oldQuantityParticipant+= newQuantityParticipant;
                        tourEntitySaved.setCurrentParticipant(oldQuantityParticipant);
                        // Luu vo bang tours
                        tourRepository.save(tourEntitySaved);

                        isSuccess = true;
                    }
                }
            }

        }catch(Exception e){
            throw new InsertBookingException(e.getMessage());
        }

        return isSuccess;
    }

    @Override
    public boolean updateBookingById(InsertBookingRequest request, int id) {
        boolean isSuccess = false;
        try{
            // Check idTour co ton tai?
            if(tourRepository.existsTourEntityById(request.getIdTour())){
                // idUser nữa sẽ lấy từ token sau
                if(userRepository.existsUserEntityById(request.getIdUser())){
                    // check num of people hop le khong?
                    TourEntity tourEntitySaved = tourRepository.findById(request.getIdTour());
                    UserEntity userEntitySaved = userRepository.findById(request.getIdUser());
                    BookingEntity bookingSaved = bookingRepository.findById(id);
                    int oldQuantityParticipant = bookingSaved.getNumberOfPeople();
                    int currentQuantityParticipant = tourEntitySaved.getCurrentParticipant();
                    int newQuantityParticipant = request.getNumOfPeople();
                    int quantityNeedToUpdate = newQuantityParticipant - oldQuantityParticipant;

                    if(quantityNeedToUpdate + currentQuantityParticipant <= tourEntitySaved.getMaxParticipant() && newQuantityParticipant > 0){

                        bookingSaved.setTour(tourEntitySaved);
                        bookingSaved.setUser(userEntitySaved);
                        bookingSaved.setNumberOfPeople(newQuantityParticipant);
                        bookingSaved.setBookingDate(LocalDateTime.now());
                        double newTotalPrice = tourEntitySaved.getPrice() * newQuantityParticipant;
                        bookingSaved.setTotalPrice(newTotalPrice);
                        // Khong cap nhat status
                        // Luu vo bang bookings:
                        bookingRepository.save(bookingSaved);

                        // Check truong hop het ve
                        int sumQuantityParticipantPerTour = bookingRepository.sumPeopleOfTourById(request.getIdTour());
                        if(sumQuantityParticipantPerTour == tourEntitySaved.getMaxParticipant()){
                            tourEntitySaved.setStatus("Hết vé");
                        } else {
                            tourEntitySaved.setStatus("Còn trống");
                        }
                        tourEntitySaved.setCurrentParticipant(currentQuantityParticipant + quantityNeedToUpdate);
                        // Luu vo bang tours:
                        tourRepository.save(tourEntitySaved);
                        isSuccess = true;
                    } else{
                        return isSuccess;
                    }

                }
            }
        }catch (Exception e){
            throw new InsertBookingException(e.getMessage());
        }
        return isSuccess;
    }

    @Override
    public List<Admin_BookingDTO> getAllBookingByAdmin() {
        List<Admin_BookingDTO> listBookingDTO = new ArrayList<>();
        List<BookingEntity> bookingEntityList = bookingRepository.findAll();
        if(bookingEntityList.isEmpty()){
            throw new ObjectNotFoundException("Không tìm thấy bất kỳ thông tin Booking nào");
        }
        bookingEntityList.forEach(item ->{
            Admin_BookingDTO bookingDTO = new Admin_BookingDTO();
            bookingDTO.setIdBooking(item.getId());
            bookingDTO.setTourBooking(item.getTour().getName());
            bookingDTO.setUserBooking(item.getUser().getFullname());
            bookingDTO.setEmailUser(item.getUser().getEmail());
            bookingDTO.setBookingDate(item.getBookingDate());
            bookingDTO.setNumOfPeople(item.getNumberOfPeople());
            bookingDTO.setStatus(item.getStatus());
            bookingDTO.setTotalPrice(item.getTotalPrice());

            listBookingDTO.add(bookingDTO);
        });
        return listBookingDTO;
    }

}
