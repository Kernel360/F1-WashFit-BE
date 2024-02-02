package com.kernel360.ecolife.repository;

import com.kernel360.ecolife.entity.ViolatedProduct;
import com.kernel360.ecolife.entity.ViolatedProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViolatedProductRepository extends JpaRepository<ViolatedProduct, ViolatedProductId> {
}
