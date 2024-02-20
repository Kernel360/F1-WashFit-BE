package com.kernel360.likes.entity;

import com.kernel360.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name = "likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_id_gen")
    @SequenceGenerator(name = "like_id_gen", sequenceName = "likes_like_no_seq")
    @Column(name = "like_no", nullable = false)
    private Long id;

    @JoinColumn(name = "member_id")
    private String memberId;

    @JoinColumn(name = "product_no")
    private Long productNo;

    private Like( String memberId, Long productNo) {
        this.memberId = memberId;
        this.productNo = productNo;
    }

    public static Like of(
            String memberId,
            Long productNo
    ){
        return new Like(
                memberId, productNo
        );
    }
}
