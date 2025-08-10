package com.airline.baggage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "airports")
public class Airport extends BaseEntity {

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

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String city;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String country;

    @Size(max = 50)
    private String timezone;

    @Column(precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "airport", fetch = FetchType.LAZY)
    private List<ScanningLocation> scanningLocations;

    // Constructors, getters, and setters
    public Airport() {}

    public Airport(String iataCode, String name, String city, String country) {
        this.iataCode = iataCode;
        this.name = name;
        this.city = city;
        this.country = country;
    }

    // Getters and setters (generate in IDE)
    public String getIataCode() { return iataCode; }
    public void setIataCode(String iataCode) { this.iataCode = iataCode; }

    public String getIcaoCode() { return icaoCode; }
    public void setIcaoCode(String icaoCode) { this.icaoCode = icaoCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }

    public BigDecimal getLatitude() { return latitude; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }

    public BigDecimal getLongitude() { return longitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public List<ScanningLocation> getScanningLocations() { return scanningLocations; }
    public void setScanningLocations(List<ScanningLocation> scanningLocations) { this.scanningLocations = scanningLocations; }
}