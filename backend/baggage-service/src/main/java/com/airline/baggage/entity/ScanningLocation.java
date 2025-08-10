package com.airline.baggage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "scanning_locations")
public class ScanningLocation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airport_id")
    private Airport airport;

    @NotBlank
    @Size(max = 50)
    @Column(name = "location_type", nullable = false)
    private String locationType;

    @NotBlank
    @Size(max = 100)
    @Column(name = "location_name", nullable = false)
    private String locationName;

    @Size(max = 20)
    @Column(name = "location_code")
    private String locationCode;

    @Column(nullable = false)
    private Boolean active = true;

    // Constructors, getters, setters
    public ScanningLocation() {}

    public ScanningLocation(Airport airport, String locationType, String locationName) {
        this.airport = airport;
        this.locationType = locationType;
        this.locationName = locationName;
    }

    // Getters and setters
    public Airport getAirport() { return airport; }
    public void setAirport(Airport airport) { this.airport = airport; }

    public String getLocationType() { return locationType; }
    public void setLocationType(String locationType) { this.locationType = locationType; }

    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }

    public String getLocationCode() { return locationCode; }
    public void setLocationCode(String locationCode) { this.locationCode = locationCode; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}