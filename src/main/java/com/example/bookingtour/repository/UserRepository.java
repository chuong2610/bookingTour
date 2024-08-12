package com.example.bookingtour.repository;

import com.example.bookingtour.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {
    boolean existsUserEntityById(int id);
    UserEntity findById(int id);

    UserEntity findUserEntityById(int id);

}
