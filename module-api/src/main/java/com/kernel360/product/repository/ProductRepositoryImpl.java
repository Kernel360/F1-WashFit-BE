package com.kernel360.product.repository;

import com.kernel360.likes.entity.QLike;
import com.kernel360.member.entity.Member;
import com.kernel360.member.repository.MemberRepository;
import com.kernel360.product.dto.ProductResponse;
import com.kernel360.product.dto.ProductSearchDto;
import com.kernel360.product.dto.QProductResponse;
import com.kernel360.product.entity.QProduct;
import com.kernel360.utils.JWT;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.kernel360.likes.entity.QLike.*;
import static com.kernel360.product.entity.QProduct.*;
import static org.springframework.util.StringUtils.*;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryDsl {

    private final JPAQueryFactory query;
    private final MemberRepository memberRepository;

    @Override
    public Page<ProductResponse> findAllByCondition(ProductSearchDto condition, Pageable pageable) {

        String memberId = JWT.ownerId(condition.token());
        Member member = memberRepository.findOneById(memberId);

        List<ProductResponse> products =
                query.select(new QProductResponse(
                                product.productNo,
                                product.productName,
                                product.companyName,
                                product.item,
                                getIsLiked(like, product, member),
                                like.count()
                        ))
                        .from(product)
                        .leftJoin(like).on(product.productNo.eq(like.productNo))
                        .where(keywordMatching(condition.keyword()))
                        .groupBy(product.productNo)
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .orderBy(sort(condition.sortType().toString()))
                        .fetch();

        long total = query
                .selectFrom(product)
                .where(keywordMatching(condition.keyword()))
                .stream().count();

        return new PageImpl<>(products, pageable, total);
    }


    private static Expression<Boolean> getIsLiked(QLike qLike, QProduct qProduct, Member member) {
        return ExpressionUtils.as(
                JPAExpressions
                        .selectOne()
                        .from(qLike)
                        .where(
                                qLike.productNo.eq(qProduct.productNo)
                                        .and(qLike.memberNo.eq(member.getMemberNo()))
                        ).exists(),
                "isLiked"
        );
    }

    private BooleanExpression keywordMatching(String keyword) {
        return hasText(keyword) ?
                product.productName.contains(keyword)
                        .or(product.companyName.contains(keyword)
                                .or(product.item.contains(keyword)))
                : null;
    }

    private static OrderSpecifier<?> sort(String sortType) {

        if ("violation-products".equals(sortType)) {
            return new OrderSpecifier<>(Order.DESC, product.safetyStatus);
        }

        if ("recent-order".equals(sortType)) {
            return product.issuedDate.desc();
        }

        if ("recommend-order".equals(sortType)) {
            return like.count().desc();
        }
        return product.viewCount.desc();

    }


}
