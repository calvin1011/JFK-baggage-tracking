package com.airline.baggage.controller;

import com.airline.baggage.entity.Baggage;
import com.airline.baggage.entity.BaggageEvent;
import com.airline.baggage.service.BaggageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/baggage")
@CrossOrigin(origins = "*")
public class BaggageController {

    private final BaggageService baggageService;

    @Autowired
    public BaggageController(BaggageService baggageService) {
        this.baggageService = baggageService;
    }

    // Get baggage by tag number
    @GetMapping("/{bagTagNumber}")
    public ResponseEntity<Baggage> getBaggage(@PathVariable String bagTagNumber) {
        Optional<Baggage> baggage = baggageService.findByBagTagNumber(bagTagNumber);
        return baggage.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    // Get all baggage
    @GetMapping
    public List<Baggage> getAllBaggage() {
        return baggageService.findAll();
    }

    // Register new baggage
    @PostMapping
    public ResponseEntity<Baggage> registerBaggage(@RequestBody Baggage baggage) {
        try {
            Baggage registeredBaggage = baggageService.registerBaggage(baggage);
            return ResponseEntity.ok(registeredBaggage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update baggage status
    @PutMapping("/{bagTagNumber}/status")
    public ResponseEntity<Baggage> updateStatus(
            @PathVariable String bagTagNumber,
            @RequestParam Baggage.BaggageStatus status,
            @RequestParam(required = false) String location) {
        try {
            Baggage updatedBaggage = baggageService.updateBaggageStatus(bagTagNumber, status, location);
            return ResponseEntity.ok(updatedBaggage);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Scan baggage
    @PostMapping("/{bagTagNumber}/scan")
    public ResponseEntity<Baggage> scanBaggage(
            @PathVariable String bagTagNumber,
            @RequestParam String location,
            @RequestParam String scannedBy) {
        try {
            Baggage scannedBaggage = baggageService.scanBaggage(bagTagNumber, location, scannedBy);
            return ResponseEntity.ok(scannedBaggage);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get baggage history
    @GetMapping("/{bagTagNumber}/history")
    public ResponseEntity<List<BaggageEvent>> getBaggageHistory(@PathVariable String bagTagNumber) {
        try {
            List<BaggageEvent> history = baggageService.getBaggageHistory(bagTagNumber);
            return ResponseEntity.ok(history);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Search baggage by passenger
    @GetMapping("/passenger/{passengerId}")
    public List<Baggage> getBaggageByPassenger(@PathVariable Long passengerId) {
        return baggageService.findByPassengerId(passengerId);
    }

    // Search baggage by flight
    @GetMapping("/flight/{flightId}")
    public List<Baggage> getBaggageByFlight(@PathVariable Long flightId) {
        return baggageService.findByFlightId(flightId);
    }

    // Get baggage by status
    @GetMapping("/status/{status}")
    public List<Baggage> getBaggageByStatus(@PathVariable Baggage.BaggageStatus status) {
        return baggageService.findByStatus(status);
    }

    // Get baggage in transit
    @GetMapping("/in-transit")
    public List<Baggage> getBaggageInTransit() {
        return baggageService.findBaggageInTransit();
    }

    // Get delayed baggage
    @GetMapping("/delayed")
    public List<Baggage> getDelayedBaggage() {
        return baggageService.findDelayedBaggage();
    }
}