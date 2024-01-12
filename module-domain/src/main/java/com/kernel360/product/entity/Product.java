package com.kernel360.product.entity;

import com.kernel360.base.BaseEntity;
import com.kernel360.brand.entity.Brand;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_gen")
    @SequenceGenerator(name = "product_id_gen", sequenceName = "product_product_no_seq", allocationSize = 50)
    @Column(name = "product_no", nullable = false)
    private Integer productNo;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "declare_no", nullable = false)
    private String declareNo;

    @Column(name = "is_violation", nullable = false)
    private Boolean isViolation = false;

    @Column(name = "view_count", nullable = false)
    private Integer viewCount;

    @ManyToOne
    @JoinColumn(name = "brand_no")
    private Brand brand;


    private Product(
            String productName,
            String barcode,
            String description,
            String declareNo,
            Boolean isViolation,
            Integer viewCount
    ) {
        this.productName = productName;
        this.barcode = barcode;
        this.description = description;
        this.declareNo = declareNo;
        this.isViolation = isViolation;
        this.viewCount = viewCount;
    }

    public static Product of(
            String productName,
            String barcode,
            String description,
            String declareNo,
            Boolean isViolation,
            Integer viewCount
    ) {
        return new Product(productName, barcode, description, declareNo, isViolation, viewCount);
    }
}