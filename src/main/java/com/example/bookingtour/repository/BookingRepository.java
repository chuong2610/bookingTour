package com.example.bookingtour.repository;

import com.example.bookingtour.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {

    BookingEntity getBookingEntityById(int id);

    BookingEntity findById(int id);

    @Query(value = "SELECT SUM(b.number_of_people) AS totalQuantity FROM bookings b WHERE b.id_tour = :idTour GROUP BY b.id_tour", nativeQuery = true)
    int sumPeopleOfTourById(@Param("idTour") int id);

}
