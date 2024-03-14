package com.kernel360.member.dto;


import java.time.LocalDateTime;


public record MemberDto(Long memberNo,
                        String id,
                        String email,
                        String password,
                        String gender,
                        String age,
                        LocalDateTime createdAt,
                        String createdBy,
                        LocalDateTime modifiedAt,
                        String modifiedBy
) {

    public static MemberDto of(
            Long memberNo,
            String id,
            String email,
            String password,
            String gender,
            String age,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime modifiedAt,
            String modifiedBy
    ) {
        return new MemberDto(
                memberNo,
                id,
                email,
                password,
                gender,
                age,
                createdAt,
                createdBy,
                modifiedAt,
                modifiedBy
        );
    }


}