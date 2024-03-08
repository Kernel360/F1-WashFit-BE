package com.kernel360.bbs.repository;

import com.kernel360.bbs.dto.BBSDto;
import com.kernel360.bbs.entity.BBS;
import com.kernel360.bbs.enumset.BBSType;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.kernel360.bbs.entity.QBBS.bBS;
import static com.kernel360.member.entity.QMember.member;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class BBSRepositoryDSLImpl implements BBSRepositoryDSL {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BBS> getBBSWithCondition(String bbsType, String keyword, Pageable pageable) {

        Predicate finalPredicate = bBS.isVisible.eq(true)
                                                .and(bBS.type.eq(BBSType.valueOf(bbsType).name()))
                                                .and(keywordContains(keyword));

        List<BBS> bbs = getBBSWithMember().
                                            where(finalPredicate)
                                            .orderBy(bBS.createdAt.desc())
                                            .offset(pageable.getOffset())
                                            .limit(pageable.getPageSize())
                                            .fetch();

        Long totalCount = queryFactory
                .select(bBS.count())
                .from(bBS)
                .where(
                        keywordContains(keyword),
                        bBS.isVisible.eq(true)
                )
                .fetchOne();

        return new PageImpl<>(bbs, pageable, totalCount);
        //return PageableExecutionUtils.getPage(bbs, pageable, totalCount);
    }

    private JPAQuery<BBS> getBBSWithMember() {
        return queryFactory
                .select(bBS)
                .from(bBS)
                .join(member).on(bBS.member.memberNo.eq(member.memberNo));
    }

    private BooleanExpression keywordContains(String keyword) {
        return hasText(keyword) ?
                bBS.title.contains(keyword)
               .or(bBS.contents.contains(keyword))
               .or(bBS.member.id.eq(keyword))
                : null;
    }

}
