package com.airline.baggage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "baggage")
public class Baggage extends BaseEntity {

    @NotBlank
    @Size(max = 20)
    @Column(name = "bag_tag_number", unique = true, nullable = false)
    private String bagTagNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @Column(precision = 5, scale = 2)
    private BigDecimal weight;

    @Size(max = 50)
    private String dimensions;

    @Enumerated(EnumType.STRING)
    @Column(name = "bag_type")
    private BagType bagType = BagType.CHECKED;

    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.NORMAL;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_status")
    private BaggageStatus currentStatus = BaggageStatus.CHECKED_IN;

    @Size(max = 100)
    @Column(name = "current_location")
    private String currentLocation;

    @Size(max = 3)
    @Column(name = "final_destination")
    private String finalDestination;

    @OneToMany(mappedBy = "baggage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BaggageEvent> events;

    // Enums
    public enum BagType {
        CHECKED, CARRY_ON, OVERSIZED, SPECIAL
    }

    public enum Priority {
        NORMAL, HIGH, PRIORITY, VIP
    }

    public enum BaggageStatus {
        CHECKED_IN, IN_TRANSIT, LOADED, IN_FLIGHT, ARRIVED,
        CLAIM_READY, CLAIMED, DELAYED, LOST, DAMAGED
    }

    // Constructors, getters, and setters
    public Baggage() {}

    public Baggage(String bagTagNumber, Passenger passenger, Flight flight) {
        this.bagTagNumber = bagTagNumber;
        this.passenger = passenger;
        this.flight = flight;
    }

    // Generate getters and setters in IDE
    public String getBagTagNumber() { return bagTagNumber; }
    public void setBagTagNumber(String bagTagNumber) { this.bagTagNumber = bagTagNumber; }

    public Passenger getPassenger() { return passenger; }
    public void setPassenger(Passenger passenger) { this.passenger = passenger; }

    public Flight getFlight() { return flight; }
    public void setFlight(Flight flight) { this.flight = flight; }

    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }

    public String getDimensions() { return dimensions; }
    public void setDimensions(String dimensions) { this.dimensions = dimensions; }

    public BagType getBagType() { return bagType; }
    public void setBagType(BagType bagType) { this.bagType = bagType; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public BaggageStatus getCurrentStatus() { return currentStatus; }
    public void setCurrentStatus(BaggageStatus currentStatus) { this.currentStatus = currentStatus; }

    public String getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(String currentLocation) { this.currentLocation = currentLocation; }

    public String getFinalDestination() { return finalDestination; }
    public void setFinalDestination(String finalDestination) { this.finalDestination = finalDestination; }

    public List<BaggageEvent> getEvents() { return events; }
    public void setEvents(List<BaggageEvent> events) { this.events = events; }
}