package com.airline.baggage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "aircraft")
public class Aircraft extends BaseEntity {

    @NotBlank
    @Size(max = 20)
    @Column(unique = true, nullable = false)
    private String registration;

    @Size(max = 50)
    @Column(name = "aircraft_type")
    private String aircraftType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airline_id")
    private Airline airline;

    private Integer capacity;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "aircraft", fetch = FetchType.LAZY)
    private List<Flight> flights;

    // Constructors, getters, setters
    public Aircraft() {}

    public Aircraft(String registration, String aircraftType, Airline airline) {
        this.registration = registration;
        this.aircraftType = aircraftType;
        this.airline = airline;
    }

    // Getters and setters
    public String getRegistration() { return registration; }
    public void setRegistration(String registration) { this.registration = registration; }

    public String getAircraftType() { return aircraftType; }
    public void setAircraftType(String aircraftType) { this.aircraftType = aircraftType; }

    public Airline getAirline() { return airline; }
    public void setAirline(Airline airline) { this.airline = airline; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public List<Flight> getFlights() { return flights; }
    public void setFlights(List<Flight> flights) { this.flights = flights; }
}