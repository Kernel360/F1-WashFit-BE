package com.kernel360.ecolife.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportedProduct extends ReportedProductDetails {

    @Id
    @Column(name = "mst_id")
    private String productMasterId;

    @Column(name = "prdt_nm")
    private String productName;

    @Column(name = "slfsfcfst_no")
    private String safetyReportNumber;

    @Column(name = "item")
    private String item;

    @Column(name = "est_no")
    private int estNumber;

    @Column(name = "reg_date")
    private LocalDateTime registeredDate;

    @Column(name = "comp_nm")
    private String companyName;

    private ReportedProduct(String productMasterId,
                            String productName,
                            String safetyReportNumber,
                            String item,
                            int estNumber,
                            LocalDateTime registeredDate,
                            String companyName
    ) {
        this.productMasterId = productMasterId;
        this.productName = productName;
        this.safetyReportNumber = safetyReportNumber;
        this.item = item;
        this.estNumber = estNumber;
        this.registeredDate = registeredDate;
        this.companyName = companyName;
    }

    public static ReportedProduct of(String productMasterId,
                                     String productName,
                                     String safetyReportNumber,
                                     String item,
                                     int estNumber,
                                     LocalDateTime registeredDate,
                                     String companyName) {
        return new ReportedProduct(productMasterId, productName, safetyReportNumber, item, estNumber, registeredDate,
                companyName);
    }

}
