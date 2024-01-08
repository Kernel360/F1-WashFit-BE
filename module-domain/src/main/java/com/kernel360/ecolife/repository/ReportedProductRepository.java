package com.kernel360.ecolife.repository;

import com.kernel360.ecolife.entity.ReportedProduct;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportedProductRepository extends JpaRepository<ReportedProduct, String> {
    @Query("SELECT rp FROM ReportedProduct rp WHERE rp.productMasterId = :productMasterId")
    Optional<ReportedProduct> findByProductMasterId(@Param("productMasterId") String productMasterId);

}
