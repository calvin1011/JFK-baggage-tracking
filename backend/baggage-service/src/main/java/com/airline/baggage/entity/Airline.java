package com.airline.baggage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "airlines")
public class Airline extends BaseEntity {

    @NotBlank
    @Size(max = 3)
    @Column(name = "iata_code", unique = true, nullable = false)
    private String iataCode;

    @Size(max = 4)
    @Column(name = "icao_code", unique = true)
    private String icaoCode;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String name;

    @Size(max = 100)
    private String country;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "airline", fetch = FetchType.LAZY)
    private List<Flight> flights;

    @OneToMany(mappedBy = "airline", fetch = FetchType.LAZY)
    private List<Aircraft> aircraft;

    // Constructors, getters, setters
    public Airline() {}

    public Airline(String iataCode, String name) {
        this.iataCode = iataCode;
        this.name = name;
    }

    // Getters and setters
    public String getIataCode() { return iataCode; }
    public void setIataCode(String iataCode) { this.iataCode = iataCode; }

    public String getIcaoCode() { return icaoCode; }
    public void setIcaoCode(String icaoCode) { this.icaoCode = icaoCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public List<Flight> getFlights() { return flights; }
    public void setFlights(List<Flight> flights) { this.flights = flights; }

    public List<Aircraft> getAircraft() { return aircraft; }
    public void setAircraft(List<Aircraft> aircraft) { this.aircraft = aircraft; }
}