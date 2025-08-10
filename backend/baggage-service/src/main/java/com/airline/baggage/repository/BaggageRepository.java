package com.airline.baggage.repository;

import com.airline.baggage.entity.Baggage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BaggageRepository extends JpaRepository<Baggage, Long> {

    // Find by bag tag number (primary lookup)
    Optional<Baggage> findByBagTagNumber(String bagTagNumber);

    // Find by passenger
    List<Baggage> findByPassengerId(Long passengerId);

    // Find by flight
    List<Baggage> findByFlightId(Long flightId);

    // Find by status
    List<Baggage> findByCurrentStatus(Baggage.BaggageStatus status);

    // Find by passenger email
    @Query("SELECT b FROM Baggage b JOIN b.passenger p WHERE p.email = :email")
    List<Baggage> findByPassengerEmail(@Param("email") String email);

    // Find baggage in transit
    @Query("SELECT b FROM Baggage b WHERE b.currentStatus IN ('IN_TRANSIT', 'LOADED', 'IN_FLIGHT')")
    List<Baggage> findBaggageInTransit();

    // Find delayed baggage
    @Query("SELECT b FROM Baggage b JOIN b.flight f WHERE f.scheduledArrival < :currentTime AND b.currentStatus != 'CLAIMED'")
    List<Baggage> findDelayedBaggage(@Param("currentTime") LocalDateTime currentTime);

    // Find baggage by location
    List<Baggage> findByCurrentLocation(String location);

    // Count baggage by status
    @Query("SELECT COUNT(b) FROM Baggage b WHERE b.currentStatus = :status")
    Long countByStatus(@Param("status") Baggage.BaggageStatus status);

    // Find baggage created in date range
    @Query("SELECT b FROM Baggage b WHERE b.createdAt BETWEEN :startDate AND :endDate")
    List<Baggage> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate);
}