package com.kernel360.member.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


class MemberTest {

    private Integer memberNo;
    private String id;
    private String email;
    private String password;
    private String gender;
    private LocalDate birthdate;
    private Member member;

    @BeforeEach
    void 테스트준비() {
        memberNo = 1;
        id = "user123";
        email = "user123@example.com";
        password = "password123";
        gender = "Female";
        birthdate = LocalDate.of(1990, 1, 1);

        member = Member.of(memberNo, id, email, password, gender, birthdate);
    }

    @Test
    void 멤버객체생성시_모든필드가_적절히_들어가는지_확인() {
        assertThat(member.getMemberNo()).isEqualTo(memberNo);
        assertThat(member.getId()).isEqualTo(id);
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getPassword()).isEqualTo(password);
        assertThat(member.getGender()).isEqualTo(gender);
        assertThat(member.getBirthdate()).isEqualTo(birthdate);
    }
}