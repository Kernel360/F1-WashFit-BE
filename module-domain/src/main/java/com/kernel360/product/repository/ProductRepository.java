package com.kernel360.product.repository;

import com.kernel360.product.entity.Product;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Id> {
    Optional<Product> findById(Long productId);
}
