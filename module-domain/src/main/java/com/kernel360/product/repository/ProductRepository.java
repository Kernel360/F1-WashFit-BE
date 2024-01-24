package com.kernel360.product.repository;

import com.kernel360.product.entity.Product;

import java.util.List;
import java.util.Optional;

import com.kernel360.product.entity.SafetyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM Product WHERE product_name LIKE CONCAT('%', :keyword, '%') OR barcode LIKE CONCAT('%', :keyword, '%') OR description LIKE CONCAT('%', :keyword, '%')", nativeQuery = true)
    List<Product> findByKeyword(@Param("keyword") String keyword);

    List<Product> findAllByOrderByViewCountDesc();

    List<Product> findTop5ByOrderByProductNameDesc();

    List<Product> findAllBySafetyStatusEquals(SafetyStatus safetyStatus);

    List<Product> findAllByOrderByCreatedAtDesc();

    @Query(value = "SELECT p FROM Product  p WHERE p.productName = :productName "
            + "AND p.reportNumber = :reportNumber "
            + "AND p.productType =:productType "
            + "AND p.manufactureNation = :manufactureNation")
    Optional<Product> findProductByProductNameAndReportNumber(@Param("productName") String productName,
                                                              @Param("reportNumber") String reportNumber,
                                                              @Param("productType") String productType,
                                                              @Param("manufactureNation") String manufactureNation);
}
