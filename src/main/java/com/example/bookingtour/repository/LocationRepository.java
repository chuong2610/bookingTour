package com.example.bookingtour.repository;

import com.example.bookingtour.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Integer> {

    LocationEntity findById(int id);

}
