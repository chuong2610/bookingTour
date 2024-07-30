package com.example.bookingtour.repository;

import com.example.bookingtour.entity.ImageTourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourImageRepository extends JpaRepository<ImageTourEntity, Integer> {

    List<ImageTourEntity> findAllByTour_Id(int id);

}
