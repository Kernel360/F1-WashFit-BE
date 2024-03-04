package com.kernel360.brand.entity;

import com.kernel360.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "brand")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand extends BaseEntity {
    @Id
    @Column(name = "brand_no", nullable = false)
    @SequenceGenerator(name = "brand_id_gen", sequenceName = "brand_brand_no_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_id_gen")
    private Long brandNo;

    @Column(name = "brand_name", nullable = false)
    private String brandName;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "nation_name", length = Integer.MAX_VALUE)
    private String nationName;

    private Brand(String brandName, String companyName, String description, String nationName) {
        this.brandName = brandName;
        this.companyName = companyName;
        this.description = description;
        this.nationName = nationName;
    }

    public static Brand toEntity(String brandName, String companyName, String description, String nationName) {

        return new Brand(brandName, companyName, description, nationName);
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void updateBrandCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void updateBrandNationName(String nationName) {
        this.nationName = nationName;
    }

    public void updateAll(String brandName, String companyName, String description, String nationName) {
        this.brandName = brandName;
        this.companyName = companyName;
        this.description = description;
        this.nationName = nationName;
    }
}