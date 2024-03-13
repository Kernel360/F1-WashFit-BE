package com.kernel360.bbs.repository;

import com.kernel360.bbs.dto.BBSListDto;
import com.kernel360.bbs.enumset.BBSType;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;

import static com.kernel360.bbs.entity.QBBS.bBS;
import static com.kernel360.member.entity.QMember.member;
import static com.querydsl.core.types.Projections.fields;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class BBSRepositoryDSLImpl implements BBSRepositoryDSL {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BBSListDto> getBBSWithCondition(String type, String keyword, Pageable pageable) {

        Predicate finalPredicate = bBS.isVisible.eq(true)
                                   .and(bBS.type.eq(BBSType.valueOf(type).name()));

        List<BBSListDto> bbs = getBBSListWithMember().
                                            where(
                                                    finalPredicate,
                                                    keywordContains(keyword)
                                            )
                                            .orderBy(bBS.createdAt.desc())
                                            .offset(pageable.getOffset())
                                            .limit(pageable.getPageSize())
                                            .fetch();

        Long totalCount = queryFactory
                .select(bBS.count())
                .from(bBS)
                .where(
                        finalPredicate,
                        keywordContains(keyword)
                )
                .fetchOne();

        return new PageImpl<>(bbs, pageable, totalCount);
    }

    private JPAQuery<BBSListDto> getBBSListWithMember() {
        return queryFactory
                .select(fields(BBSListDto.class,
                        bBS.bbsNo,
                        bBS.title,
                        bBS.type,
                        bBS.createdAt,
                        bBS.createdBy,
                        bBS.viewCount,
                        bBS.member.memberNo,
                        bBS.member.id
                        )

                )
                .from(bBS)
                .join(member).on(bBS.member.memberNo.eq(member.memberNo));
    }

    private BooleanExpression keywordContains(String keyword) {   //우선 참이 된다면 하위 조건 결과는 포함 안됨.
        return hasText(keyword) ?
                bBS.title.contains(keyword)
               .or(bBS.contents.contains(keyword))
               .or(bBS.createdBy.eq(keyword))
                : null;
    }

}
