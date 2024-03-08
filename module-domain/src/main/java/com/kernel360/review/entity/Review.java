package com.kernel360.review.entity;

import com.kernel360.base.BaseEntity;
import com.kernel360.member.entity.Member;
import com.kernel360.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_id_gen")
    @SequenceGenerator(name = "review_id_gen", sequenceName = "review_review_no_seq")
    @Column(name = "review_no", nullable = false)
    private Long reviewNo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_no", nullable = false, updatable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_no", nullable = false, updatable = false)
    private Member member;

    @Column(name = "star_rating", nullable = false, precision = 3, scale = 1)
    private BigDecimal starRating;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "contents", nullable = false, length = 4000)
    private String contents;

    @Column(name = "is_visible", nullable = false)
    private Boolean isVisible;

    private Review(Long reviewNo, Product product, Member member, BigDecimal starRating, String title, String contents, Boolean isVisible) {
        this.reviewNo = reviewNo;
        this.product = product;
        this.member = member;
        this.starRating = starRating;
        this.title = title;
        this.contents = contents;
        this.isVisible = isVisible;
    }

    public Review(Long reviewNo, BigDecimal starRating, String title, String contents, Boolean isVisible) {
        this.reviewNo = reviewNo;
        this.starRating = starRating;
        this.title = title;
        this.contents = contents;
        this.isVisible = isVisible;
    }

    public static Review of(Long reviewNo, Product product, Member member, BigDecimal starRating, String title, String contents, Boolean isVisible) {
        return new Review(reviewNo, product, member, starRating, title, contents, isVisible);
    }

    public static Review of(Long reviewNo, BigDecimal starRating, String title, String contents, Boolean isVisible) {
        return new Review(reviewNo, starRating, title, contents, isVisible);
    }
}