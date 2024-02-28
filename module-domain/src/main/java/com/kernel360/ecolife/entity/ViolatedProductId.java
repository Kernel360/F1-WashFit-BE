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
public class ViolatedProductId implements Serializable {

    @Column(name = "product_master_no")
    private String productMasterNo;

    @Column(name = "product_arm_code")
    private String productArmCode;

    public ViolatedProductId(String productMasterNo, String productArmCode) {
        this.productMasterNo = productMasterNo;
        this.productArmCode = productArmCode;
    }
}
