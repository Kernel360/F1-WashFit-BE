package com.kernel360.carinfo.entity;

import com.kernel360.base.BaseEntity;
import com.kernel360.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "car_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_info_id_gen")
    @SequenceGenerator(name = "car_info_id_gen", sequenceName = "car_info_car_no_seq")
    @Column(name = "car_no", nullable = false)
    private Long carNo;

    @OneToOne
    @JoinColumn(name = "member_no")
    private Member member;

    @Column(name = "car_type")
    private Integer carType;

    @Column(name = "car_size")
    private Integer carSize;

    @Column(name = "car_color")
    private Integer carColor;

    @Column(name = "driving_env")
    private Integer drivingEnv;

    @Column(name = "parking_env")
    private Integer parkingEnv;

    public CarInfo(Integer carType, Integer carSize, Integer carColor, Integer drivingEnv, Integer parkingEnv) {
        this.carType = carType;
        this.carSize = carSize;
        this.carColor = carColor;
        this.drivingEnv = drivingEnv;
        this.parkingEnv = parkingEnv;
    }

    public static CarInfo of(Integer carType, Integer carSize, Integer carColor, Integer drivingEnv, Integer parkingEnv) {
        return new CarInfo(carType, carSize, carColor, drivingEnv, parkingEnv);
    }
}