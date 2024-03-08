package com.kernel360.bbs.repository;

import com.kernel360.bbs.dto.BBSDto;
import com.kernel360.bbs.entity.BBS;
import com.kernel360.file.entity.FileReferType;
import com.kernel360.review.dto.ReviewSearchDto;
import com.kernel360.review.dto.ReviewSearchResult;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.kernel360.member.entity.QMember.member;
import static com.kernel360.product.entity.QProduct.product;
import static com.kernel360.review.entity.QReview.review;
import static com.querydsl.core.types.ExpressionUtils.orderBy;
import static com.querydsl.core.types.dsl.Expressions.stringTemplate;

@RequiredArgsConstructor
public class BBSRepositoryDSLImpl implements BBSRepositoryDSL {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BBSDto> getBBSWithCondition(String sortType, String keyword, Pageable pageable) {

        List<BBSDto> bbs = getBBSWithMember().
                where(
                        titleLike
                ).
                .orderBy(sort(sortType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> totalCountQuery = queryFactory
                .select(bbs.count())
                .from(bbs)
                .where(
                        bbs.isVisible.eq(true)
                );

        return PageableExecutionUtils.getPage(bbs, pageable, totalCountQuery::fetchOne);
    }

    @Override
    public Page<ReviewSearchResult> findAllByCondition(ReviewSearchDto condition, Pageable pageable) {
        List<ReviewSearchResult> reviews =
                getJoinedResults()
                        .where(
                                productNoEq(condition.productNo()), // 조건절 메서드 별도 생성
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

    private JPAQuery<BBSDto> getBBSWithMember() {
        return queryFactory
                .select(Projections.fields(BBSDto.class,
                        bbs.bbsNo,
                        bbs.title,
                        bbs.contents,
                        bbs.createdAt,
                        bbs.createdBy,
                        bbs.modifiedAt,
                        bbs.modifiedBy,
                        member.memberNo,
                        member.id,
                        member.age,
                        member.gender
                ))
                .from(bbs)
                .join(member);
    }
}
