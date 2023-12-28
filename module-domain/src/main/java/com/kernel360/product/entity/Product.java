package com.kernel360.product.entity;

import com.kernel360.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "product")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_gen")
    @SequenceGenerator(name = "product_id_gen", sequenceName = "product_product_no_seq", allocationSize = 50)
    @Column(name = "product_no", nullable = false)
    private Integer productNo;

    @Column(name = "product_name", nullable = false, length = Integer.MAX_VALUE)
    private String productName;

    @Column(name = "barcode", length = Integer.MAX_VALUE)
    private String barcode;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "declare_no", nullable = false, length = Integer.MAX_VALUE)
    private String declareNo;

    @Column(name = "is_violation", nullable = false)
    private Boolean isViolation = false;

    @Column(name = "view_count", nullable = false)
    private Integer viewCount;

}