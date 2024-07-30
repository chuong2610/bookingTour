package com.example.bookingtour.repository;

import com.example.bookingtour.entity.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<TourEntity, Integer> {

    boolean existsTourEntityById(int id);

    TourEntity findById(int id);


}
