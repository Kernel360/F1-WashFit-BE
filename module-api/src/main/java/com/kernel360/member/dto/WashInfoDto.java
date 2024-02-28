package com.kernel360.member.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.kernel360.washinfo.entity.WashInfo;


public record WashInfoDto(
        @JsonProperty(value = "frequency") Integer washCount,
        @JsonProperty(value = "cost") Integer monthlyExpense,
        @JsonProperty(value = "interest") Integer interest
) {
    public static WashInfoDto of(
            Integer washCount,
            Integer monthlyExpense,
            Integer interest
    ) {
        return new WashInfoDto(
                washCount,
                monthlyExpense,
                interest
        );
    }

    public WashInfo toEntity() {
        return WashInfo.of(
                this.washCount,
                this.monthlyExpense,
                this.interest
        );
    }

    public static WashInfoDto from(WashInfo entity) {

        return WashInfoDto.of(
                entity.getWashCount(),
                entity.getMonthlyExpense(),
                entity.getInterest()
        );
    }
}
