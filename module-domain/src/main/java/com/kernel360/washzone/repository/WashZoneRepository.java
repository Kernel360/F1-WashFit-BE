package com.kernel360.washzone.repository;

import com.kernel360.washzone.entity.WashZone;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WashZoneRepository extends JpaRepository<WashZone, Id> {
    @Query("SELECT wz FROM WashZone wz WHERE wz.latitude BETWEEN ?1 AND ?2 AND wz.longitude BETWEEN ?3 AND ?4")
    List<WashZone> findWashZonesWithinBounds(Double minLat, Double maxLat, Double minLng, Double maxLng);
}
