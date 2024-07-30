package com.example.bookingtour.repository;

import com.example.bookingtour.entity.ImageLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LocationImageRepository extends JpaRepository<ImageLocationEntity, Integer> {
    boolean existsImageLocationEntityByLocation_Id(int id);

    @Transactional
    void deleteByLocation_Id(int id);

    List<ImageLocationEntity> findAllByLocation_Id(int id);
}
