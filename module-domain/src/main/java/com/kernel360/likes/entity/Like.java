package com.kernel360.likes.entity;

import com.kernel360.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name = "likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_no", "product_no"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_id_gen")
    @SequenceGenerator(name = "like_id_gen", sequenceName = "likes_like_no_seq")
    @Column(name = "like_no", nullable = false)
    private Long id;

    @JoinColumn(name = "member_no")
    private Long memberNo;

    @JoinColumn(name = "product_no")
    private Long productNo;

    private Like( Long memberNo, Long productNo) {
        this.memberNo = memberNo;
        this.productNo = productNo;
    }

    public static Like of(
            Long memberNo,
            Long productNo
    ){
        return new Like(
                memberNo, productNo
        );
    }
}
