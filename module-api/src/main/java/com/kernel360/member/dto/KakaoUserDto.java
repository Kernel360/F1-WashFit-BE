package com.kernel360.member.dto;

public record KakaoUserDto(
        String id,
        String email
) {
    public static KakaoUserDto of(
            String id,
            String email
    ){
        return new KakaoUserDto(
            id,
            email
        );
    }
}
