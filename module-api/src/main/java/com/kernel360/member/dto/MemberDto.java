package com.kernel360.member.dto;

import com.kernel360.member.entity.Member;
import com.kernel360.member.enumset.Age;
import com.kernel360.member.enumset.Gender;

import java.time.LocalDate;

/**
 * DTO for {@link com.kernel360.member.entity.Member}
 */
public record MemberDto(Long memberNo,
                        String id,
                        String email,
                        String password,
                        String gender,
                        String age,
                        LocalDate createdAt,
                        String createdBy,
                        LocalDate modifiedAt,
                        String modifiedBy,
                        String jwtToken
) {

    public static MemberDto of(
            Long memberNo,
            String id,
            String email,
            String password,
            String gender,
            String age,
            LocalDate createdAt,
            String createdBy,
            LocalDate modifiedAt,
            String modifiedBy,
            String jwtToken
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
                modifiedBy,
                jwtToken
        );
    }

    public static MemberDto from(Member entity) {
        return MemberDto.of(
                entity.getMemberNo(),
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                Gender.ordinalToName(entity.getGender()),
                Age.ordinalToValue(entity.getAge()),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy(),
                null
        );
    }

    public Member toEntity() {
        return Member.of(
                this.memberNo(),
                this.id(),
                this.email(),
                this.password(),
                Gender.valueOf(this.gender()).ordinal(),
                Age.valueOf(this.age()).ordinal()
        );
    }

    /** joinMember **/
    public static MemberDto of(
            String id,
            String email,
            String password,
            String gender,
            String   age
    ){
        return new MemberDto(
                null,
                id,
                email,
                password,
                gender,
                age,
                null,
                null,
                null,
                null,
                null
        );
    }

    /** Login Binding **/
    public static MemberDto login(Member entity, String jwtToken) {
        return MemberDto.of(
                entity.getMemberNo(),
                entity.getId(),
                entity.getEmail(),
                null,
                Gender.ordinalToName(entity.getGender()),
                Age.ordinalToValue(entity.getAge()),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy(),
                jwtToken
        );
    }

    /** Request Login **/
    public static MemberDto of(
            String id,
            String password
    ){
        return new MemberDto(
                null,
                id,
                null,
                password,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }
}