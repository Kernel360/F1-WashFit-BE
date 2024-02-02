package com.kernel360.member.dto;

import com.kernel360.carinfo.entity.CarInfo;

public record CarInfoDto(
        Integer carType,
        Integer carSize,
        Integer carColor,
        Integer drivingEnv,
        Integer parkingEnv
) {

    public static CarInfoDto of(
            Integer carType,
            Integer carSize,
            Integer carColor,
            Integer drivingEnv,
            Integer parkingEnv
    ) {
        return new CarInfoDto(
                carType,
                carSize,
                carColor,
                drivingEnv,
                parkingEnv
        );
    }

    public CarInfo toEntity() {
        return CarInfo.of(
            this.carType,
            this.carSize,
            this.carColor,
            this.drivingEnv,
            this.parkingEnv
        );
    }

    public CarInfoDto from(CarInfo entity) {
        return CarInfoDto.of(
                entity.getCarType(),
                entity.getCarSize(),
                entity.getCarColor(),
                entity.getDrivingEnv(),
                entity.getParkingEnv()
        );
    }
}