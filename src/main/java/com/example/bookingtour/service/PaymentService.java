package com.example.bookingtour.service;

import com.example.bookingtour.dto.Admin_PaymentDTO;
import com.example.bookingtour.dto.Admin_BookingDTO;
import com.example.bookingtour.entity.BookingEntity;
import com.example.bookingtour.entity.PaymentEntity;
import com.example.bookingtour.exception.InsertPaymentException;
import com.example.bookingtour.exception.ObjectNotFoundException;
import com.example.bookingtour.payload.request.InsertPaymentRequest;
import com.example.bookingtour.repository.BookingRepository;
import com.example.bookingtour.repository.PaymentRepository;
import com.example.bookingtour.service.imp.PaymentServiceImp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService implements PaymentServiceImp {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public boolean insertPayment(InsertPaymentRequest insertPaymentRequest) {
        boolean isSuccess = false;
        try{
            PaymentEntity paymentExist = paymentRepository.findPaymentEntityByBooking_Id(insertPaymentRequest.getIdBooking());
            if(paymentExist == null){
                PaymentEntity paymentEntity = new PaymentEntity();
                paymentEntity.setPaymentDate(LocalDateTime.now());
                paymentEntity.setPaymentMethod(insertPaymentRequest.getPaymentMethod());

                BookingEntity bookingSaved = bookingRepository.getBookingEntityById(insertPaymentRequest.getIdBooking());
                paymentEntity.setBooking(bookingSaved);
                // Lay total price --> amount
                paymentEntity.setAmount(bookingSaved.getTotalPrice());

                paymentRepository.save(paymentEntity);

                // Thanh toan xong thi set status cho booking la Da thanh toan
                // --> update lai status --> get bookingEntity tu cai idBooking cua payment --> cap nhat lai status
                if(bookingSaved.getStatus().equals("Chưa thanh toán") || bookingSaved.getStatus() == null){
                    bookingSaved.setStatus("Đã thanh toán");
                }

                bookingRepository.save(bookingSaved);

                isSuccess = true;
            }
        }catch (Exception e){
            throw new InsertPaymentException(e.getMessage());
        }
        return isSuccess;
    }

    @JsonIgnore
    @Override
    public List<Admin_PaymentDTO> getAllPaymentByAdmin() {
        List<Admin_PaymentDTO> listAdminPaymentDTO = new ArrayList<>();

        List<PaymentEntity> paymentEntityList = paymentRepository.findAll();
        if(paymentEntityList.isEmpty()){
            throw new ObjectNotFoundException("Không tìm thấy bất kỳ thông tin Payment nào");
        }
        paymentEntityList.forEach(item -> {
            Admin_PaymentDTO payment = new Admin_PaymentDTO();
            payment.setIdPayment(item.getId());
            BookingEntity bookingEntity = bookingRepository.findById(item.getBooking().getId());
            if(bookingEntity != null){
                Admin_BookingDTO bookingDTO = new Admin_BookingDTO();
                bookingDTO.setIdBooking(bookingEntity.getTour().getId());
                bookingDTO.setTourBooking(bookingEntity.getTour().getName());
                bookingDTO.setUserBooking(bookingEntity.getUser().getFullname());
                bookingDTO.setEmailUser(bookingEntity.getUser().getEmail());
                bookingDTO.setNumOfPeople(bookingEntity.getNumberOfPeople());
                bookingDTO.setBookingDate(bookingEntity.getBookingDate());
                bookingDTO.setTotalPrice(bookingEntity.getTotalPrice());
                bookingDTO.setStatus(bookingEntity.getStatus());

                payment.setBooking(bookingDTO);
            }
            payment.setAmount(item.getAmount());
            payment.setPaymentDate(item.getPaymentDate());
            payment.setPaymentMethod(item.getPaymentMethod());

            listAdminPaymentDTO.add(payment);
        });

        return listAdminPaymentDTO;
    }
}
