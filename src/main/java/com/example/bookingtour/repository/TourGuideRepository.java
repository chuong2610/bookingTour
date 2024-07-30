package com.example.bookingtour.repository;

import com.example.bookingtour.entity.TourGuideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourGuideRepository extends JpaRepository<TourGuideEntity, Integer> {

    TourGuideEntity findById(int id);

}
