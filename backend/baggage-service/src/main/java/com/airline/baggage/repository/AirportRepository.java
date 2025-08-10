package com.airline.baggage.repository;

import com.airline.baggage.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

    // Find by IATA code
    Optional<Airport> findByIataCode(String iataCode);

    // Find by ICAO code
    Optional<Airport> findByIcaoCode(String icaoCode);

    // Find by country
    List<Airport> findByCountry(String country);

    // Find by city
    List<Airport> findByCity(String city);

    // Search airports by name
    @Query("SELECT a FROM Airport a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Airport> findByNameContaining(@Param("name") String name);

    // Find active airports
    List<Airport> findByActiveTrue();
}