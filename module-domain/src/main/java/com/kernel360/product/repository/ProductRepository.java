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
    List<Product> findByProductNameContaining(String keyword);

    List<Product> findAllByOrderByViewCountDesc();

    List<Product> findTop5ByOrderByProductNameDesc();

    List<Product> findAllBySafetyStatusEquals(SafetyStatus safetyStatus);

    List<Product> findAllByOrderByCreatedAtDesc();

    @Query(value = "SELECT p FROM Product p WHERE p.productName = :productName "
            + "AND p.companyName like :companyName")
    Optional<Product> findProductByProductNameAndCompanyName(@Param("productName") String productName,
                                                             @Param("companyName") String companyName);
}
