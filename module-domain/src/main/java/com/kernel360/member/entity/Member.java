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

    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private CarInfo carInfo;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private WashInfo washInfo;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "gender")
    private int gender;

    @Column(name = "age")
    private int age;

    @Column(name = "account_type")
    private String accountType;

    public static Member of(Long memberNo, String id, String email, String password, int gender, int age, String accountType) {

        return new Member(memberNo, id, email, password, gender, age, accountType);
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
            int age,
            String accountType
    ) {
        this.memberNo = memberNo;
        this.id = id;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.accountType = accountType;
    }

    /**
     * joinMember
     **/
    public static Member createJoinMember(String id, String email, String password, int gender, int age, String accountType) {

        return new Member(id, email, password, gender, age, accountType);
    }

    /**
     * joinMember Binding
     **/
    private Member(String id, String email, String password, int gender, int age, String accountType) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.accountType = accountType;
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

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateWashInfo(WashInfo washInfo) {
        this.washInfo = washInfo;
    }

    public void updateCarInfo(CarInfo carInfo) {
        this.carInfo = carInfo;
    }

    public void updateFromInfo(int gender, int age) {
        this.gender = gender;
        this.age = age;
    }

    /**
     * review request
     **/
    private Member (Long memberNo) {
        this.memberNo = memberNo;
    }

    /**
     * review request
     **/
    public static Member of(Long memberNo) {
        return new Member(memberNo);
    }
}