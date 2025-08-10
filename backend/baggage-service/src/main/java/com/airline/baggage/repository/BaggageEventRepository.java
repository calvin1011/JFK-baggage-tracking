package com.airline.baggage.repository;

import com.airline.baggage.entity.BaggageEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BaggageEventRepository extends JpaRepository<BaggageEvent, Long> {

    // Find events by baggage ID (ordered by timestamp)
    List<BaggageEvent> findByBaggageIdOrderByTimestampDesc(Long baggageId);

    // Find events by event type
    List<BaggageEvent> findByEventType(String eventType);

    // Find events in date range
    List<BaggageEvent> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    // Find events by location
    List<BaggageEvent> findByLocation(String location);

    // Find latest event for baggage
    @Query("SELECT e FROM BaggageEvent e WHERE e.baggage.id = :baggageId ORDER BY e.timestamp DESC")
    List<BaggageEvent> findLatestEventByBaggageId(@Param("baggageId") Long baggageId);

    // Find events by scanner
    List<BaggageEvent> findByScannedBy(String scannedBy);
}