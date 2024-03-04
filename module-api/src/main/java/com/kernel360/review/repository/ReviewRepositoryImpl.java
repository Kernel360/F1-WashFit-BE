package com.kernel360.review.repository;

import com.kernel360.review.dto.ReviewSearchDto;
import com.kernel360.review.entity.Review;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.kernel360.review.entity.QReview.review;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Review> findAllByCondition(ReviewSearchDto condition, Pageable pageable) {
        List<Review> reviews = queryFactory
                .select(review)
                .from(review)
                .where(
                        productNoEq(condition.productNo()),
                        memberNoEq(condition.memberNo()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(sort(condition.sortBy()))
                .fetch();

        Long totalCount = queryFactory
                .select(review.count())
                .from(review)
                .where(
                        productNoEq(condition.productNo()),
                        memberNoEq(condition.memberNo()))
                .fetchOne();

        return new PageImpl<>(reviews, pageable, totalCount);
    }

    private BooleanExpression productNoEq(Long productNo) {
        return productNo == null ? null : review.product.productNo.eq(productNo);
    }

    private BooleanExpression memberNoEq(Long memberNo) {
        return memberNo == null ? null : review.member.memberNo.eq(memberNo);
    }

    private static OrderSpecifier<? extends Number> sort(String sortBy) {
        if ("topRated".equals(sortBy)) {
            return review.starRating.desc();
        }

        if ("lowRated".equals(sortBy)) {
            return review.starRating.asc();
        }

        return review.reviewNo.desc();
    }
}
