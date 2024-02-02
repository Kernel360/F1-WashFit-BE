package com.kernel360.member.dto;

public record MemberInfo(String id,
                         String email,
                         int gender,
                         int age
                         ) {
    static MemberInfo of(
            String id,
            String email,
            int gender,
            int age
    ) {
        return new MemberInfo(id, email, gender, age);
    }
}