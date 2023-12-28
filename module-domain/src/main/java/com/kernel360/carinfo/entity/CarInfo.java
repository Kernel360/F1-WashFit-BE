package com.kernel360.carinfo.entity;

import com.kernel360.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "car_info")
public class CarInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_info_id_gen")
    @SequenceGenerator(name = "car_info_id_gen", sequenceName = "car_info_car_no_seq", allocationSize = 50)
    @Column(name = "car_no", nullable = false)
    private Integer carNo;

    @Column(name = "car_brand", length = Integer.MAX_VALUE)
    private String carBrand;

    @Column(name = "car_type", length = Integer.MAX_VALUE)
    private String carType;

    @Column(name = "car_size", length = Integer.MAX_VALUE)
    private String carSize;

    @Column(name = "pearl")
    private Boolean pearl;

    @Column(name = "clear_coat")
    private Boolean clearCoat;

}