package com.kernel360.member.dto;

public record FindMemberIdFromEmailDto(String email) {
    public static FindMemberIdFromEmailDto of(String email) {
        return new FindMemberIdFromEmailDto(email);
    }
}
