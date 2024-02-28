package com.kernel360.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kernel360.carinfo.entity.CarInfo;

public record CarInfoDto(
        @JsonProperty(value = "cartype") Integer carType,
        @JsonProperty(value = "segment") Integer carSize,
        @JsonProperty(value = "color") Integer carColor,
        @JsonProperty(value = "driving") Integer drivingEnv,
        @JsonProperty(value ="parking") Integer parkingEnv
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

    public static CarInfoDto from(CarInfo entity) {
        return CarInfoDto.of(
                entity.getCarType(),
                entity.getCarSize(),
                entity.getCarColor(),
                entity.getDrivingEnv(),
                entity.getParkingEnv()
        );
    }
}