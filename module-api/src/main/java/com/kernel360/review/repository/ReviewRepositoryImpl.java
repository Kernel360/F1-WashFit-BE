package com.kernel360.review.repository;

import com.kernel360.file.entity.FileReferType;
import com.kernel360.review.dto.ReviewResponse;
import com.kernel360.review.dto.ReviewSearchDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.kernel360.file.entity.QFile.file;
import static com.kernel360.review.entity.QReview.review;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ReviewResponse> findAllByCondition(ReviewSearchDto condition, Pageable pageable) {
        List<ReviewResponse> reviews =
                getJoinedWithFile()
                .where(
                        productNoEq(condition.productNo()),
                        memberNoEq(condition.memberNo())
                )
                .groupBy(review.reviewNo)
                .orderBy(sort(condition.sortBy()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalCountQuery = queryFactory
                .select(review.count())
                .from(review)
                .where(
                        productNoEq(condition.productNo()),
                        memberNoEq(condition.memberNo())
                );

        return PageableExecutionUtils.getPage(reviews, pageable, totalCountQuery::fetchOne);
    }

    @Override
    public ReviewResponse findByReviewNo(Long reviewNo) {
        return getJoinedWithFile()
                .where(review.reviewNo.eq(reviewNo))
                .groupBy(review.reviewNo)
                .fetchOne();
    }

    private JPAQuery<ReviewResponse> getJoinedWithFile() {
        return queryFactory
                .select(Projections.fields(ReviewResponse.class,
                        review.reviewNo,
                        review.product.productNo,
                        review.member.memberNo,
                        review.starRating,
                        review.title,
                        review.contents,
                        review.createdAt,
                        review.createdBy,
                        review.modifiedAt,
                        review.modifiedBy,
                        Expressions.stringTemplate("STRING_AGG({0}, '|')", file.fileUrl).as("fileUrls")
                ))
                .from(review)
                .leftJoin(file)
                .on(
                        file.referenceType.eq(FileReferType.REVIEW.getCode()),
                        file.referenceNo.eq(review.reviewNo)
                );
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
