package com.kernel360.member.dto;


import com.kernel360.carinfo.entity.CarInfo;
import com.kernel360.washinfo.entity.WashInfo;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberResponse {
    private Long memberNo;
    private String id;
    private String email;
    private int gender;
    private int age;
    private LocalDateTime registerDate;
    private String accountType;
    private WashInfo washInfo;
    private CarInfo carInfo;
    @QueryProjection
    public MemberResponse(Long memberNo, String id, String email, int gender, int age, LocalDateTime registerDate,
            String accountType, WashInfo washInfo, CarInfo carInfo) {
        this.memberNo = memberNo;
        this.id = id;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.registerDate = registerDate;
        this.accountType = accountType;
        this.washInfo = washInfo;
        this.carInfo = carInfo;
    }

}
