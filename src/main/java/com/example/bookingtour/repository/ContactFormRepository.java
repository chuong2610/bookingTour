package com.example.bookingtour.repository;

import com.example.bookingtour.entity.ContactFormEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactFormRepository extends JpaRepository<ContactFormEntity, Integer> {
    ContactFormEntity findById(int id);
}
