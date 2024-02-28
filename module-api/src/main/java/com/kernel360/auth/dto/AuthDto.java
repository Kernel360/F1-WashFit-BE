package com.kernel360.auth.dto;

public record AuthDto(
        String jwtToken
) {
    public static AuthDto of(
            String jwtToken
    ){
        return new AuthDto(
                jwtToken
        );
    }
}
