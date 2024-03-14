package com.kernel360.member.dto;

import com.kernel360.carinfo.entity.CarInfo;
import com.kernel360.washinfo.entity.WashInfo;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MemberSearchDto(
        String id,
        String name,
        String email,
        String age,
        LocalDateTime registerDate,
        WashInfo washInfo,
        CarInfo carInfo
) {
}
