package com.airline.baggage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "baggage_events")
public class BaggageEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baggage_id", nullable = false)
    private Baggage baggage;

    @NotBlank
    @Size(max = 50)
    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "event_description", columnDefinition = "TEXT")
    private String eventDescription;

    @Size(max = 100)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airport_id")
    private Airport airport;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    @Size(max = 100)
    @Column(name = "scanned_by")
    private String scannedBy;

    @Size(max = 50)
    @Column(name = "equipment_id")
    private String equipmentId;

    @Column(columnDefinition = "TEXT")
    private String notes;

    // Constructors
    public BaggageEvent() {}

    public BaggageEvent(Baggage baggage, String eventType, String location) {
        this.baggage = baggage;
        this.eventType = eventType;
        this.location = location;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Baggage getBaggage() { return baggage; }
    public void setBaggage(Baggage baggage) { this.baggage = baggage; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getEventDescription() { return eventDescription; }
    public void setEventDescription(String eventDescription) { this.eventDescription = eventDescription; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Airport getAirport() { return airport; }
    public void setAirport(Airport airport) { this.airport = airport; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getScannedBy() { return scannedBy; }
    public void setScannedBy(String scannedBy) { this.scannedBy = scannedBy; }

    public String getEquipmentId() { return equipmentId; }
    public void setEquipmentId(String equipmentId) { this.equipmentId = equipmentId; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}