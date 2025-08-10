package com.airline.baggage.repository;

import com.airline.baggage.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    // Find by flight number
    Optional<Flight> findByFlightNumber(String flightNumber);

    // Find by departure airport
    List<Flight> findByDepartureAirportIataCode(String airportCode);

    // Find by arrival airport
    List<Flight> findByArrivalAirportIataCode(String airportCode);

    // Find flights departing in date range
    List<Flight> findByScheduledDepartureBetween(LocalDateTime start, LocalDateTime end);

    // Find flights by status
    List<Flight> findByStatus(Flight.FlightStatus status);

    // Find current flights (departing today)
    @Query("SELECT f FROM Flight f WHERE DATE(f.scheduledDeparture) = CURRENT_DATE")
    List<Flight> findTodaysFlights();

    // Find flights with baggage
    @Query("SELECT DISTINCT f FROM Flight f JOIN f.baggage b")
    List<Flight> findFlightsWithBaggage();

    // Find delayed flights
    @Query("SELECT f FROM Flight f WHERE f.actualDeparture > f.scheduledDeparture")
    List<Flight> findDelayedFlights();
}