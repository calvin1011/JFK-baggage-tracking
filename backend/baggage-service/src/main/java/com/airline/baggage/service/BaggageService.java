package com.airline.baggage.service;

import com.airline.baggage.entity.Baggage;
import com.airline.baggage.entity.BaggageEvent;

import java.util.List;
import java.util.Optional;

public interface BaggageService {

    // Core CRUD operations
    Baggage saveBaggage(Baggage baggage);
    Optional<Baggage> findById(Long id);
    Optional<Baggage> findByBagTagNumber(String bagTagNumber);
    List<Baggage> findAll();
    void deleteBaggage(Long id);

    // Business operations
    Baggage registerBaggage(Baggage baggage);
    Baggage updateBaggageStatus(String bagTagNumber, Baggage.BaggageStatus status, String location);
    Baggage scanBaggage(String bagTagNumber, String location, String scannedBy);

    // Search operations
    List<Baggage> findByPassengerId(Long passengerId);
    List<Baggage> findByFlightId(Long flightId);
    List<Baggage> findByStatus(Baggage.BaggageStatus status);
    List<Baggage> findBaggageInTransit();

    // Event tracking
    List<BaggageEvent> getBaggageHistory(String bagTagNumber);
    BaggageEvent addBaggageEvent(String bagTagNumber, String eventType, String location, String scannedBy);

    // Reporting
    Long countBaggageByStatus(Baggage.BaggageStatus status);
    List<Baggage> findDelayedBaggage();
}