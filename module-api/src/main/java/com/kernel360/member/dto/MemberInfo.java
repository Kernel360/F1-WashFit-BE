package com.kernel360.member.dto;

import com.kernel360.member.entity.Member;

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


    //
    public Member toEntity() {
        return Member.of(
                this.id,
                this.email,
                this.gender,
                this.age

        );
    }
}