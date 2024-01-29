package com.kernel360.ecolife.repository;

import com.kernel360.ecolife.entity.ConcernedProduct;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcernedProductRepository extends JpaRepository<ConcernedProduct, String> {
    @Query(value = "SELECT cp.* FROM concerned_product cp "
            + "JOIN LATERAL (SELECT cp2.prdt_name, MAX(cp2.issued_date) AS max_issu_date "
            + "FROM concerned_product cp2 WHERE cp2.comp_nm LIKE :companyName AND cp2.prdt_name LIKE :brandName GROUP BY cp2.prdt_name) max_dates "
            + "ON cp.prdt_name = max_dates.prdt_name AND cp.issued_date = max_dates.max_issu_date "
            + "ORDER BY max_dates.max_issu_date DESC", nativeQuery = true)
    List<ConcernedProduct> findByBrandNameAndCompanyName(@Param("brandName") String brandName,
                                                         @Param("companyName") String companyName);
}
