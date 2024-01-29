package com.kernel360.auth.entity;

import com.kernel360.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Entity
@DynamicUpdate
@Table(name = "auth")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auth extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_id_gen")
    @SequenceGenerator(name = "auth_id_gen", sequenceName = "auth_auth_no_seq")
    @Column(name = "auth_no", nullable = false)
    private Long authNo;

    @Column(name = "member_no")
    private Long memberNo;

    @Column(name = "jwt_token")
    private String jwtToken;

    @Column(name = "sns_token")
    private String snsToken;


    /**
     * all create
     **/
    public static Auth of(Long authNo, Long memberNo, String jwtToken, String snsToken) {

        return new Auth(authNo, memberNo, jwtToken, snsToken);
    }

    /**
     * all binding
     **/
    private Auth(Long authNo, Long memberNo, String jwtToken, String snsToken) {
        this.authNo = authNo;
        this.memberNo = memberNo;
        this.jwtToken = jwtToken;
        this.snsToken = snsToken;
    }


    public static Auth jwt(Long authNo, Long memberNo, String jwtToken) {

        return new Auth(authNo, memberNo, jwtToken);
    }

    /**
     * JWT
     **/
    private Auth(Long authNo, Long memberNo, String jwtToken) {
        this.authNo = authNo;
        this.memberNo = memberNo;
        this.jwtToken = jwtToken;
    }

    public void updateJwt(String encryptToken) {
        this.jwtToken = encryptToken;
    }

}