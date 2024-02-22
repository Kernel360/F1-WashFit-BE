package com.kernel360.member.dto;

public record KakaoUserDto(
        String id,
        String email,
        int age,
        int gender
) {
    public static KakaoUserDto of(
            String id,
            String email,
            int age,
            int gender
    ){
        return new KakaoUserDto(
            id,
            email,
            age,
            gender
        );
    }
}
