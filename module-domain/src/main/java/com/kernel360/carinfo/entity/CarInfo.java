package com.kernel360.carinfo.entity;

import com.kernel360.base.BaseEntity;
import com.kernel360.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "car_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_info_id_gen")
    @SequenceGenerator(name = "car_info_id_gen", sequenceName = "car_info_car_no_seq")
    @Column(nullable = false)
    private Long carNo;

    @OneToOne
    @JoinColumn(name = "member_no")
    private Member member;

    private Integer carType;
    private Integer carSize;
    private Integer carColor;
    private Integer drivingEnv;
    private Integer parkingEnv;

    public CarInfo(Integer carType, Integer carSize, Integer carColor, Integer drivingEnv, Integer parkingEnv) {
        this.carType = carType;
        this.carSize = carSize;
        this.carColor = carColor;
        this.drivingEnv = drivingEnv;
        this.parkingEnv = parkingEnv;
    }

    public static CarInfo of(Integer carType, Integer carSize, Integer carColor, Integer drivingEnv,
                             Integer parkingEnv) {
        return new CarInfo(carType, carSize, carColor, drivingEnv, parkingEnv);
    }

    public void settingMember(Member member) {
        this.member = member;
    }

    public void updateCarInfo(Integer carType, Integer carSize, Integer carColor, Integer drivingEnv,
                              Integer parkingEnv) {
        this.carType = carType;
        this.carColor = carColor;
        this.carSize = carSize;
        this.drivingEnv = drivingEnv;
        this.parkingEnv = parkingEnv;
    }
}