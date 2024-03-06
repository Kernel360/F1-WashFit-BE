package com.kernel360.review.repository;

import com.kernel360.file.entity.FileReferType;
import com.kernel360.review.dto.ReviewSearchDto;
import com.kernel360.review.dto.ReviewSearchResult;
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
import static com.kernel360.member.entity.QMember.member;
import static com.kernel360.product.entity.QProduct.product;
import static com.kernel360.review.entity.QReview.review;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ReviewSearchResult> findAllByCondition(ReviewSearchDto condition, Pageable pageable) {
        List<ReviewSearchResult> reviews =
                getJoinedResults()
                        .where(
                                productNoEq(condition.productNo()),
                                memberNoEq(condition.memberNo())
                        )
                        .groupBy(review.reviewNo, member.memberNo, member.id, member.age, member.gender, product.productNo)
                        .orderBy(sort(condition.sortBy()))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        JPAQuery<Long> totalCountQuery = queryFactory
                .select(review.count())
                .from(review)
                .where(
                        review.isVisible.eq(true),
                        productNoEq(condition.productNo()),
                        memberNoEq(condition.memberNo())
                );

        return PageableExecutionUtils.getPage(reviews, pageable, totalCountQuery::fetchOne);
    }

    @Override
    public ReviewSearchResult findByReviewNo(Long reviewNo) {
        return getJoinedResults()
                .where(
                        review.isVisible.eq(true),
                        review.reviewNo.eq(reviewNo)
                )
                .groupBy(review.reviewNo, member.memberNo, member.id, member.age, member.gender, product.productNo)
                .fetchOne();
    }

    private JPAQuery<ReviewSearchResult> getJoinedResults() {
        return queryFactory
                .select(Projections.fields(ReviewSearchResult.class,
                        review.reviewNo,
                        review.starRating,
                        review.title,
                        review.contents,
                        review.createdAt,
                        review.createdBy,
                        review.modifiedAt,
                        review.modifiedBy,
                        member.memberNo,
                        member.id,
                        member.age,
                        member.gender,
                        product.productNo,
                        product.productName,
                        product.companyName,
                        product.imageSource,
                        product.upperItem,
                        product.item,
                        Expressions.stringTemplate("STRING_AGG({0}, '|')", file.fileUrl).as("fileUrls")
                ))
                .from(review)
                .leftJoin(file)
                .on(
                        file.referenceType.eq(FileReferType.REVIEW.getCode()),
                        file.referenceNo.eq(review.reviewNo)
                )
                .join(member)
                .on(review.member.memberNo.eq(member.memberNo))
                .join(product)
                .on(review.product.productNo.eq(product.productNo));
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
