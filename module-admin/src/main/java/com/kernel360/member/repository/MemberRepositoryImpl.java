package com.kernel360.member.repository;


import com.kernel360.member.dto.MemberResponse;
import com.kernel360.member.dto.QMemberResponse;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.kernel360.carinfo.entity.QCarInfo.*;
import static com.kernel360.member.entity.QMember.*;
import static com.kernel360.washinfo.entity.QWashInfo.*;


@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryDsl {

    private final JPAQueryFactory query;

    @Override
    public Page<MemberResponse> findAllMember(Pageable pageable) {
        List<MemberResponse> members = query.select(new QMemberResponse(
                        member.memberNo,
                        member.id,
                        member.email,
                        member.gender,
                        member.age,
                        member.createdAt,
                        member.accountType,
                        washInfo,
                        carInfo
                ))
                .from(member)
                .leftJoin(member.washInfo, washInfo).on(IsWashInfoNotNull())
                .leftJoin(member.carInfo, carInfo).on(IsCarInfoNotNull())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(member.memberNo.desc())
                .fetch();

        return PageableExecutionUtils.getPage(members, pageable, members::size);
    }

    private static BooleanExpression IsCarInfoNotNull() {
        return member.carInfo.carNo.isNotNull();
    }

    private static BooleanExpression IsWashInfoNotNull() {
        return member.washInfo.washNo.isNotNull();
    }


}
