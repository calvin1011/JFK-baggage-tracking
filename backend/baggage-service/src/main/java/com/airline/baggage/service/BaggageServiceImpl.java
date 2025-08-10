package com.airline.baggage.service.impl;

import com.airline.baggage.entity.Baggage;
import com.airline.baggage.entity.BaggageEvent;
import com.airline.baggage.repository.BaggageEventRepository;
import com.airline.baggage.repository.BaggageRepository;
import com.airline.baggage.service.BaggageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BaggageServiceImpl implements BaggageService {

    private static final Logger logger = LoggerFactory.getLogger(BaggageServiceImpl.class);

    private final BaggageRepository baggageRepository;
    private final BaggageEventRepository baggageEventRepository;

    @Autowired
    public BaggageServiceImpl(BaggageRepository baggageRepository,
                             BaggageEventRepository baggageEventRepository) {
        this.baggageRepository = baggageRepository;
        this.baggageEventRepository = baggageEventRepository;
    }

    @Override
    public Baggage saveBaggage(Baggage baggage) {
        logger.debug("Saving baggage: {}", baggage.getBagTagNumber());
        return baggageRepository.save(baggage);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Baggage> findById(Long id) {
        return baggageRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Baggage> findByBagTagNumber(String bagTagNumber) {
        return baggageRepository.findByBagTagNumber(bagTagNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Baggage> findAll() {
        return baggageRepository.findAll();
    }

    @Override
    public void deleteBaggage(Long id) {
        logger.debug("Deleting baggage with ID: {}", id);
        baggageRepository.deleteById(id);
    }

    @Override
    public Baggage registerBaggage(Baggage baggage) {
        logger.info("Registering new baggage: {}", baggage.getBagTagNumber());

        // Set initial status
        baggage.setCurrentStatus(Baggage.BaggageStatus.CHECKED_IN);

        // Save baggage
        Baggage savedBaggage = baggageRepository.save(baggage);

        // Create initial event
        addBaggageEvent(baggage.getBagTagNumber(), "CHECKED_IN",
                      "Check-in Counter", "SYSTEM");

        logger.info("Successfully registered baggage: {}", savedBaggage.getBagTagNumber());
        return savedBaggage;
    }

    @Override
    public Baggage updateBaggageStatus(String bagTagNumber, Baggage.BaggageStatus status, String location) {
        logger.info("Updating baggage status: {} to {}", bagTagNumber, status);

        Optional<Baggage> baggageOpt = baggageRepository.findByBagTagNumber(bagTagNumber);
        if (baggageOpt.isEmpty()) {
            throw new RuntimeException("Baggage not found: " + bagTagNumber);
        }

        Baggage baggage = baggageOpt.get();
        Baggage.BaggageStatus oldStatus = baggage.getCurrentStatus();

        // Update status and location
        baggage.setCurrentStatus(status);
        if (location != null) {
            baggage.setCurrentLocation(location);
        }

        Baggage updatedBaggage = baggageRepository.save(baggage);

        // Create status change event
        addBaggageEvent(bagTagNumber, "STATUS_CHANGE", location, "SYSTEM");

        logger.info("Updated baggage {} status from {} to {}",
                   bagTagNumber, oldStatus, status);

        return updatedBaggage;
    }

    @Override
    public Baggage scanBaggage(String bagTagNumber, String location, String scannedBy) {
        logger.info("Scanning baggage: {} at location: {}", bagTagNumber, location);

        Optional<Baggage> baggageOpt = baggageRepository.findByBagTagNumber(bagTagNumber);
        if (baggageOpt.isEmpty()) {
            throw new RuntimeException("Baggage not found: " + bagTagNumber);
        }

        Baggage baggage = baggageOpt.get();

        // Update current location
        baggage.setCurrentLocation(location);

        // Determine status based on location type
        Baggage.BaggageStatus newStatus = determineStatusFromLocation(location);
        if (newStatus != null) {
            baggage.setCurrentStatus(newStatus);
        }

        Baggage updatedBaggage = baggageRepository.save(baggage);

        // Create scan event
        addBaggageEvent(bagTagNumber, "SCANNED", location, scannedBy);

        logger.info("Successfully scanned baggage: {} at {}", bagTagNumber, location);
        return updatedBaggage;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Baggage> findByPassengerId(Long passengerId) {
        return baggageRepository.findByPassengerId(passengerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Baggage> findByFlightId(Long flightId) {
        return baggageRepository.findByFlightId(flightId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Baggage> findByStatus(Baggage.BaggageStatus status) {
        return baggageRepository.findByCurrentStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Baggage> findBaggageInTransit() {
        return baggageRepository.findBaggageInTransit();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BaggageEvent> getBaggageHistory(String bagTagNumber) {
        Optional<Baggage> baggageOpt = baggageRepository.findByBagTagNumber(bagTagNumber);
        if (baggageOpt.isEmpty()) {
            throw new RuntimeException("Baggage not found: " + bagTagNumber);
        }

        return baggageEventRepository.findByBaggageIdOrderByTimestampDesc(baggageOpt.get().getId());
    }

    @Override
    public BaggageEvent addBaggageEvent(String bagTagNumber, String eventType, String location, String scannedBy) {
        Optional<Baggage> baggageOpt = baggageRepository.findByBagTagNumber(bagTagNumber);
        if (baggageOpt.isEmpty()) {
            throw new RuntimeException("Baggage not found: " + bagTagNumber);
        }

        BaggageEvent event = new BaggageEvent();
        event.setBaggage(baggageOpt.get());
        event.setEventType(eventType);
        event.setLocation(location);
        event.setScannedBy(scannedBy);
        event.setTimestamp(LocalDateTime.now());

        // Set description based on event type
        event.setEventDescription(generateEventDescription(eventType, location));

        return baggageEventRepository.save(event);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countBaggageByStatus(Baggage.BaggageStatus status) {
        return baggageRepository.countByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Baggage> findDelayedBaggage() {
        return baggageRepository.findDelayedBaggage(LocalDateTime.now());
    }

    // Helper methods
    private Baggage.BaggageStatus determineStatusFromLocation(String location) {
        if (location == null) return null;

        String locationLower = location.toLowerCase();

        if (locationLower.contains("check-in") || locationLower.contains("counter")) {
            return Baggage.BaggageStatus.CHECKED_IN;
        } else if (locationLower.contains("security") || locationLower.contains("screening")) {
            return Baggage.BaggageStatus.IN_TRANSIT;
        } else if (locationLower.contains("sorting") || locationLower.contains("facility")) {
            return Baggage.BaggageStatus.IN_TRANSIT;
        } else if (locationLower.contains("loading") || locationLower.contains("gate")) {
            return Baggage.BaggageStatus.LOADED;
        } else if (locationLower.contains("claim") || locationLower.contains("carousel")) {
            return Baggage.BaggageStatus.CLAIM_READY;
        }

        return null; // Don't change status if location type is unknown
    }

    private String generateEventDescription(String eventType, String location) {
        switch (eventType.toUpperCase()) {
            case "CHECKED_IN":
                return "Baggage checked in at " + location;
            case "SCANNED":
                return "Baggage scanned at " + location;
            case "STATUS_CHANGE":
                return "Baggage status updated at " + location;
            case "LOADED":
                return "Baggage loaded onto aircraft at " + location;
            case "UNLOADED":
                return "Baggage unloaded from aircraft at " + location;
            case "CLAIMED":
                return "Baggage claimed by passenger at " + location;
            default:
                return "Baggage event: " + eventType + " at " + location;
        }
    }
}