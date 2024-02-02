package com.kernel360.member.dto;


import com.kernel360.carinfo.entity.CarInfo;
import com.kernel360.member.entity.Member;
import com.kernel360.washinfo.entity.WashInfo;

public record WashInfoDto(
        Integer washNo,
        Integer washCount,
        Integer monthlyExpense,
        Integer interest
) {
    public static WashInfoDto of(
            Integer washNo,
            Integer washCount,
            Integer monthlyExpense,
            Integer interest
    ) {
        return new WashInfoDto(
                washNo,
                washCount,
                monthlyExpense,
                interest
        );
    }

    public WashInfo toEntity() {
        return WashInfo.of(
                this.washNo,
                this.washCount,
                this.monthlyExpense,
                this.interest
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
