package com.kernel360.member.entity;

import com.kernel360.base.BaseEntity;
import com.kernel360.carinfo.entity.CarInfo;
import com.kernel360.washinfo.entity.WashInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "member_view")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_id_gen")
    @SequenceGenerator(name = "member_id_gen", sequenceName = "member_member_no_seq")
    @Column(name = "member_no", nullable = false)
    private Long memberNo;

    @Column(name = "id", nullable = false)
    private String id;

    @OneToOne(mappedBy = "member")
    private CarInfo carInfo;

    @OneToOne(mappedBy = "member")
    private WashInfo washInfo;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "gender")
    private int gender;

    @Column(name = "age")
    private int age;

    public static Member of(Long memberNo, String id, String email, String password, int gender, int age) {

        return new Member(memberNo, id, email, password, gender, age);
    }

    /**
     * All Binding
     **/
    private Member(
            Long memberNo,
            String id,
            String email,
            String password,
            int gender,
            int age
    ) {
        this.memberNo = memberNo;
        this.id = id;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.age = age;
    }

    /**
     * joinMember
     **/
    public static Member createJoinMember(String id, String email, String password, int gender, int age) {

        return new Member(id, email, password, gender, age);
    }

    /**
     * joinMember Binding
     **/
    private Member(String id, String email, String password, int gender, int age) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.age = age;
    }

    /**
     * loginMember
     **/
    public static Member loginMember(String id, String password) {

        return new Member(id, password);
    }

    /**
     * loginMember Binding
     **/
    private Member(String id, String password) {
        this.id = id;
        this.password = password;
    }


}