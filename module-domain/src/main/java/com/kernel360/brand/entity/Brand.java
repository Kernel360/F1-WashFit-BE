package com.kernel360.brand.entity;

import com.kernel360.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "nation_name", length = Integer.MAX_VALUE)
    private String nationName;
}