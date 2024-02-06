package com.kernel360.product.repository;

import com.kernel360.product.entity.Product;

import java.util.Optional;

import com.kernel360.product.entity.SafetyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByProductNameContaining(String keyword, Pageable pageable);

    Page<Product> findAllByOrderByViewCountDesc(Pageable pageable);

    Page<Product> findTop5ByOrderByProductNameDesc(Pageable pageable);

    Page<Product> findAllBySafetyStatusEquals(SafetyStatus safetyStatus, Pageable pageable);

    Page<Product> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query(value = "SELECT p FROM Product p WHERE p.productName = :productName "
            + "AND p.companyName like :companyName")
    Optional<Product> findProductByProductNameAndCompanyName(@Param("productName") String productName,
                                                             @Param("companyName") String companyName);
}
