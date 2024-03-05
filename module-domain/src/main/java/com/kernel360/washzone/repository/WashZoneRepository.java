package com.kernel360.washzone.repository;

import com.kernel360.washzone.entity.WashZone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WashZoneRepository extends JpaRepository<WashZone, Long> {
    @Query("SELECT wz FROM WashZone wz WHERE wz.latitude BETWEEN ?1 AND ?2 AND wz.longitude BETWEEN ?3 AND ?4")
    List<WashZone> findWashZonesWithinBounds(Double minLat, Double maxLat, Double minLng, Double maxLng);


    @Query("SELECT wz FROM WashZone wz WHERE " +
            "LOWER(wz.name) LIKE LOWER(CONCAT('%',:keyword,'%')) OR " +
            "LOWER(wz.address) LIKE LOWER(CONCAT('%',:keyword,'%'))")
    Page<WashZone> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    boolean existsByAddress(String address);
}
