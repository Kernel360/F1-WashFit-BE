package com.kernel360.member.entity;

import com.kernel360.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "withdraw_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WithdrawMember extends BaseEntity {

    @Id
    @Column(name = "member_no", nullable = false)
    private Long memberNo;

    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name="ip")
    private String ip;


    public static WithdrawMember of(Member member) {

        return new WithdrawMember(member.getMemberNo(), member.getId(), member.getEmail(), null); //IP정보를 저장한다면 파라메터를 추가해야
    }

    private WithdrawMember(
            Long memberNo,
            String id,
            String email,
            String ip
    ) {
        this.memberNo = memberNo;
        this.id = id;
        this.email = email;
        this.ip = ip;
    }

}
