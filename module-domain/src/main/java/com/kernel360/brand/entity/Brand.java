package com.kernel360.brand.entity;

import com.kernel360.base.BaseEntity;
import com.kernel360.product.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
@Entity
@Table(name = "brand")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
    private List<Product> productList = new ArrayList<>();


}