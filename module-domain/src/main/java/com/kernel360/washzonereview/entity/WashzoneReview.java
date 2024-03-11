package com.kernel360.washzonereview.entity;

import com.kernel360.base.BaseEntity;
import com.kernel360.member.entity.Member;
import com.kernel360.washzone.entity.WashZone;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "washzone_review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class WashzoneReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "washzone_review_id_gen")
    @SequenceGenerator(name = "washzone_review_id_gen", sequenceName = "washzone_review_washzone_review_no_seq")
    @Column(name = "washzone_review_no", nullable = false)
    private Long washzoneReviewNo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "washzone_no", nullable = false, updatable = false)
    private WashZone washzone;

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

    public WashzoneReview(Long washzoneReviewNo, WashZone washzone, Member member, BigDecimal starRating, String title, String contents, Boolean isVisible) {
        this.washzoneReviewNo = washzoneReviewNo;
        this.washzone = washzone;
        this.member = member;
        this.starRating = starRating;
        this.title = title;
        this.contents = contents;
        this.isVisible = isVisible;
    }

    public WashzoneReview(Long washzoneReviewNo, BigDecimal starRating, String title, String contents, Boolean isVisible) {
        this.washzoneReviewNo = washzoneReviewNo;
        this.starRating = starRating;
        this.title = title;
        this.contents = contents;
        this.isVisible = isVisible;
    }

    public static WashzoneReview of(Long washzoneReviewNo, WashZone washzone, Member member, BigDecimal starRating, String title, String contents, Boolean isVisible) {
        return new WashzoneReview(washzoneReviewNo, washzone, member, starRating, title, contents, isVisible);
    }

    public static WashzoneReview of(Long washzoneReviewNo, BigDecimal starRating, String title, String contents, Boolean isVisible) {
        return new WashzoneReview(washzoneReviewNo, starRating, title, contents, isVisible);
    }
}