package com.kernel360.member.dto;


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

    public static WashInfoDto from(WashInfo entity) {

        return WashInfoDto.of(
                entity.getWashNo(),
                entity.getWashCount(),
                entity.getMonthlyExpense(),
                entity.getInterest()
        );
    }
}
