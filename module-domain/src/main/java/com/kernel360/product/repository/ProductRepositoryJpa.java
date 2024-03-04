package com.kernel360.product.repository;

import com.kernel360.product.entity.Product;
import com.kernel360.product.entity.SafetyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryJpa extends JpaRepository<Product, Long>  {

    Page<Product> findAllByOrderByViewCountDesc(Pageable pageable);

    Page<Product> findTop5ByOrderByProductNameDesc(Pageable pageable);

    @Query(value = "SELECT * FROM Product p ORDER BY RANDOM() LIMIT 20", nativeQuery = true)
    List<Product> getRecommendProductsWithRandom();

    Page<Product> findAllBySafetyStatusEquals(SafetyStatus safetyStatus, Pageable pageable);

    Page<Product> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query(value = "SELECT p FROM Product p WHERE p.productName = :productName "
            + "AND p.companyName like :companyName")
    Optional<Product> findProductByProductNameAndCompanyName(@Param("productName") String productName,
                                                             @Param("companyName") String companyName);

    @Modifying
    @Query("update Product p set p.viewCount = p.viewCount + 1 where p.productNo = :id")
    void updateViewCount(@Param("id") Long id);

    Page<Product> findProductByReportNumberEquals(String reportNo, Pageable pageable);

    @Query("SELECT p FROM Product p LEFT JOIN Like l ON p.productNo = l.productNo " +
            "WHERE (LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.companyName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.upperItem) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "GROUP BY p.productNo " +
            "ORDER BY COUNT(l) DESC")
    Page<Product> getProductWithKeywordAndOrderByRecommend(String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.companyName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.upperItem) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
    Page<Product> getProductsByKeyword(String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.companyName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.upperItem) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "ORDER BY p.viewCount DESC")
    Page<Product> getProductWithKeywordAndOrderByViewCount(String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.companyName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.upperItem) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "ORDER BY p.issuedDate DESC")
    Page<Product> getProductWithKeywordAndRecentOrder(String keyword, Pageable pageable);


    @Query("SELECT p FROM Product p WHERE (" +
            "LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.companyName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.upperItem) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            ") AND p.safetyStatus = :safetyStatus " +
            "ORDER BY p.viewCount DESC")
    Page<Product> findByProductWithKeywordAndSafetyStatus(@Param("keyword") String keyword,
                                                          @Param("safetyStatus") SafetyStatus safetyStatus,
                                                          Pageable pageable);

        Page<Product> findByProductNameContaining(String keyword, Pageable pageable);

}
