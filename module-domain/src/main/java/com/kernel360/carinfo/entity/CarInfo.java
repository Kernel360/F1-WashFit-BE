package com.kernel360.carinfo.entity;

import com.kernel360.base.BaseEntity;
import com.kernel360.member.entity.Member;
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
    @SequenceGenerator(name = "car_info_id_gen", sequenceName = "car_info_car_no_seq")
    @Column(name = "car_no", nullable = false)
    private Long carNo;

    @OneToOne
    @JoinColumn(name = "member_no")
    private Member member;

    @Column(name = "car_brand")
    private String carBrand;

    @Column(name = "car_type")
    private String carType;

    @Column(name = "car_size")
    private String carSize;

    @Column(name = "pearl")
    private Boolean pearl;

    @Column(name = "clear_coat")
    private Boolean clearCoat;

}