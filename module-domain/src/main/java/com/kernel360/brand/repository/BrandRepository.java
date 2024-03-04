package com.kernel360.brand.repository;

import com.kernel360.brand.entity.Brand;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query("SELECT b FROM Brand b where b.companyName like :companyName")
    List<Brand> findByCompanyName(@Param("companyName") String companyName);

    Optional<Brand> findBrandByBrandName(String brandName);

    Optional<Brand> findBrandByBrandNo(Long brandNo);
}
