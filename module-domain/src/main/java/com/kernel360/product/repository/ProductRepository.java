package com.kernel360.product.repository;

import com.kernel360.product.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM Product WHERE product_name LIKE CONCAT('%', :keyword, '%') OR barcode LIKE CONCAT('%', :keyword, '%') OR description LIKE CONCAT('%', :keyword, '%')", nativeQuery = true)
    List<Product> findByKeyword(@Param("keyword") String keyword);

    List<Product> findAllByOrderByViewCountDesc();

    @Query(value = "SELECT * FROM Product p WHERE p.product_name = :productName "
            + "AND p.company_name = :companyName ORDER BY p.issued_date DESC LIMIT 1", nativeQuery = true)
    Optional<Product> findProductByProductNameAndReportNumber(@Param("productName") String productName,
                                                              @Param("companyName") String companyName);
}
