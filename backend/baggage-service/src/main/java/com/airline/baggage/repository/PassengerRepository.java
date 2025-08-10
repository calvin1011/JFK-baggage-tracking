package com.airline.baggage.repository;

import com.airline.baggage.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    // Find by email
    Optional<Passenger> findByEmail(String email);

    // Find by frequent flyer number
    Optional<Passenger> findByFrequentFlyerNumber(String frequentFlyerNumber);

    // Search by name (case insensitive)
    @Query("SELECT p FROM Passenger p WHERE LOWER(p.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Passenger> findByNameContaining(@Param("name") String name);

    // Find passengers with baggage
    @Query("SELECT DISTINCT p FROM Passenger p JOIN p.baggage b")
    List<Passenger> findPassengersWithBaggage();

    // Find by phone number
    Optional<Passenger> findByPhone(String phone);
}