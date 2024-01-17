package com.kernel360.brand.entity;

import com.kernel360.base.BaseEntity;
import com.kernel360.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table(name = "brand")
public class Brand extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_id_gen")
    @SequenceGenerator(name = "brand_id_gen", sequenceName = "brand_brand_no_seq")
    @Column(name = "brand_no", nullable = false)
    private Integer brandNo;
    @Column(name = "brand_name", nullable = false, length = Integer.MAX_VALUE)
    private String brandName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
    private List<Product> productList;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "nation_name", length = Integer.MAX_VALUE)
    private String nationName;
}