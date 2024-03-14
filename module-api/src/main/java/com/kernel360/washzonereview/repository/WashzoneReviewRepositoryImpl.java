package com.kernel360.washzonereview.repository;

import com.kernel360.file.entity.FileReferType;
import com.kernel360.washzonereview.dto.WashzoneReviewSearchDto;
import com.kernel360.washzonereview.dto.WashzoneReviewSearchResult;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.kernel360.file.entity.QFile.file;
import static com.kernel360.member.entity.QMember.member;
import static com.kernel360.washzone.entity.QWashZone.washZone;
import static com.kernel360.washzonereview.entity.QWashzoneReview.washzoneReview;
import static com.querydsl.core.types.dsl.Expressions.stringTemplate;

@RequiredArgsConstructor
public class WashzoneReviewRepositoryImpl implements WashzoneReviewRepositoryDsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<WashzoneReviewSearchResult> findAllByCondition(WashzoneReviewSearchDto condition, Pageable pageable) {
        List<WashzoneReviewSearchResult> reviews =
                getJoinedResults()
                        .where(
                                washzoneNoEq(condition.washzoneNo()),
                                memberNoEq(condition.memberNo())
                        )
                        .groupBy(washzoneReview.washzoneReviewNo, member.memberNo, member.id, member.age, member.gender, washZone.washZoneNo)
                        .orderBy(sort(condition.sortBy()))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        JPAQuery<Long> totalCountQuery = queryFactory
                .select(washzoneReview.count())
                .from(washzoneReview)
                .where(
                        washzoneReview.isVisible.eq(true),
                        washzoneNoEq(condition.washzoneNo()),
                        memberNoEq(condition.memberNo())
                );

        return PageableExecutionUtils.getPage(reviews, pageable, totalCountQuery::fetchOne);
    }

    @Override
    public WashzoneReviewSearchResult findByWashzoneReviewNo(Long washzoneReviewNo) {
        return getJoinedResults()
                .where(
                        washzoneReview.isVisible.eq(true),
                        washzoneReview.washzoneReviewNo.eq(washzoneReviewNo)
                )
                .groupBy(washzoneReview.washzoneReviewNo, member.memberNo, member.id, member.age, member.gender, washZone.washZoneNo)
                .fetchOne();
    }

    private JPAQuery<WashzoneReviewSearchResult> getJoinedResults() {
        return queryFactory
                .select(Projections.fields(WashzoneReviewSearchResult.class,
                        washzoneReview.washzoneReviewNo,
                        washzoneReview.starRating,
                        washzoneReview.title,
                        washzoneReview.contents,
                        washzoneReview.createdAt,
                        washzoneReview.createdBy,
                        washzoneReview.modifiedAt,
                        washzoneReview.modifiedBy,
                        member.memberNo,
                        stringTemplate("SUBSTRING({0}, 1, 2) || REPEAT('*', LENGTH({0}) - 2)", member.id).as("id"),
                        member.age,
                        member.gender,
                        washZone.washZoneNo.as("washzoneNo"),
                        washZone.name,
                        washZone.address,
                        washZone.type,
                        stringTemplate("STRING_AGG({0}, '|')", file.fileUrl).as("fileUrls")
                ))
                .from(washzoneReview)
                .leftJoin(file)
                .on(
                        file.referenceType.eq(FileReferType.WASHZONE_REVIEW.getCode()),
                        file.referenceNo.eq(washzoneReview.washzoneReviewNo)
                )
                .join(member)
                .on(washzoneReview.member.memberNo.eq(member.memberNo))
                .join(washZone)
                .on(washzoneReview.washzone.washZoneNo.eq(washZone.washZoneNo));
    }

    private BooleanExpression washzoneNoEq(Long washzoneNo) {
        return washzoneNo == null ? null : washzoneReview.washzone.washZoneNo.eq(washzoneNo);
    }

    private BooleanExpression memberNoEq(Long memberNo) {
        return memberNo == null ? null : washzoneReview.member.memberNo.eq(memberNo);
    }

    private static OrderSpecifier<? extends Number> sort(String sortBy) {
        if ("topRated".equals(sortBy)) {
            return washzoneReview.starRating.desc();
        }

        if ("lowRated".equals(sortBy)) {
            return washzoneReview.starRating.asc();
        }

        return washzoneReview.washzoneReviewNo.desc();
    }
}
