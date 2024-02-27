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
@Table(name = "withraw_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WithrawMember extends BaseEntity {

    @Id
    @Column(name = "member_no", nullable = false)
    private Long memberNo;

    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "email", nullable = false)
    private String email;

    //TODO 아이피 추가


    public static WithrawMember of(Long memberNo, String id, String email) {

        return new WithrawMember(memberNo, id, email);
    }

    private WithrawMember(
            Long memberNo,
            String id,
            String email
    ) {
        this.memberNo = memberNo;
        this.id = id;
        this.email = email;
    }

}
