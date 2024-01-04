package com.kernel360.member.dto;

import com.kernel360.member.entity.Member;

import java.time.LocalDate;

/**
 * DTO for {@link com.kernel360.member.entity.Member}
 */
public record MemberDto(Integer memberNo,
                        String id,
                        String email,
                        String password,
                        String gender,
                        LocalDate birthdate,
                        LocalDate createdAt,
                        String createdBy,
                        LocalDate modifiedAt,
                        String modifiedBy
) {

    /** New All **/
    public static MemberDto of(
            Integer memberNo,
            String id,
            String email,
            String password,
            String gender,
            LocalDate birthdate,
            LocalDate createdAt,
            String createdBy,
            LocalDate modifiedAt,
            String modifiedBy
    ) {
        return new MemberDto(
                memberNo,
                id,
                email,
                password,
                gender,
                birthdate,
                createdAt,
                createdBy,
                modifiedAt,
                modifiedBy
        );
    }

    /** joinMember **/
    public static MemberDto of(
            String id,
            String email,
            String password
    ){
        return new MemberDto(
                null,
                id,
                email,
                password,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    /** Entity -> DTO **/
    public static MemberDto from(Member entity) {
        return MemberDto.of(
                entity.getMemberNo(),
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getGender(),
                entity.getBirthdate(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    /** DTO -> Entity All binding **/
    public static Member toEntity(MemberDto memberDto) {
        return Member.of(
                memberDto.memberNo,
                memberDto.id,
                memberDto.email,
                memberDto.password,
                memberDto.gender,
                memberDto.birthdate
        );
    }
}