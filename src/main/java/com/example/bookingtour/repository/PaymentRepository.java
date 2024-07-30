package com.example.bookingtour.repository;

import com.example.bookingtour.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {
    PaymentEntity findPaymentEntityByBooking_Id(int id);
}
