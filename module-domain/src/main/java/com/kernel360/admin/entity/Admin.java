package com.kernel360.admin.entity;


import com.kernel360.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name = "admin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_id_gen")
    @SequenceGenerator(name = "admin_id_gen", sequenceName = "admin_admin_no_seq")
    @Column(name = "admin_no", nullable = false)
    private Long adminNo;

    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    private Admin(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public static Admin of(String id, String email, String password) {

        return new Admin(id, email, password);
    }


    private Admin(
            String id,
            String email,
            String password
    ) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static Admin createJoinMember(String id, String email, String password) {

        return new Admin(id, email, password);
    }

    public static Admin loginAdmin(String id, String password) {

        return new Admin(id, password);
    }

    public void updatePassword(String password) {
        this.password = password;
    }

}
