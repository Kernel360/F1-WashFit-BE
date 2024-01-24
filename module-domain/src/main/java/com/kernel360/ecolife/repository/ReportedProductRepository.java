package com.kernel360.ecolife.repository;

import com.kernel360.ecolife.entity.ReportedProduct;
import com.kernel360.ecolife.entity.ReportedProductId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportedProductRepository extends JpaRepository<ReportedProduct, String> {
    /**
     * productMasterId + estNo 복합키를 통해 신고대상 생활화학제품을 조회
     *
     * @return Optional
     */
    @Query("SELECT rp FROM ReportedProduct rp WHERE rp.id = :id")
    Optional<ReportedProduct> findById(@Param("id") ReportedProductId reportedProductId);

    /**
     * 제조사명과 제품명을 통해서 해당 브랜드의 제품 리스트를 조회
     *
     * @return List<ReportedProduct>
     */
    @Query(value = "SELECT rp.* FROM reported_product rp "
            + "JOIN (SELECT prdt_nm, MAX(issu_date) AS max_issu_date "
            + "FROM reported_product WHERE comp_nm LIKE :companyName AND prdt_nm LIKE :brandName GROUP BY prdt_nm) max_dates "
            + "ON rp.prdt_nm = max_dates.prdt_nm AND rp.issu_date = max_dates.max_issu_date "
            + "ORDER BY max_dates.max_issu_date DESC", nativeQuery = true)
    List<ReportedProduct> findByBrandNameAndCompanyName(@Param("companyName") String companyName,
                                                        @Param("brandName") String brandName);

    /**
     * 제조사명을 통해서 해당 브랜드의 제품 리스트를 조회
     *
     * @return List<ReportedProduct>
     */
    @Query("SELECT rp FROM ReportedProduct rp WHERE rp.companyName LIKE %:companyName%")
    List<ReportedProduct> findByCompanyName(@Param("companyName") String companyName);
}
