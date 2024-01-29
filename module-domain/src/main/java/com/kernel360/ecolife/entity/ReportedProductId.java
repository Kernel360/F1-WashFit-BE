package com.kernel360.ecolife.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportedProductId implements Serializable {

    @Column(name = "mst_id")
    private String productMasterId;

    @Column(name = "est_no")
    private int estNumber;

    public ReportedProductId(String productMasterId, int estNumber) {
        this.productMasterId = productMasterId;
        this.estNumber = estNumber;
    }


}
