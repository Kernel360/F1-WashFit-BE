package com.kernel360.likes.service;

import com.kernel360.exception.BusinessException;
import com.kernel360.likes.code.LikeErrorCode;
import com.kernel360.likes.dto.LikeSearchDto;
import com.kernel360.likes.entity.Like;
import com.kernel360.likes.repository.LikeRepositoryJpa;
import com.kernel360.member.service.MemberService;
import com.kernel360.product.dto.ProductResponse;
import com.kernel360.product.repository.ProductRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepositoryJpa likeRepositoryJpa;
    private final MemberService memberService;
    private final ProductRepositoryJpa productRepositoryJpa;

    @Transactional
    public void heartOn(Long productNo, String token) {
        Long memberNo = memberService.findMemberByToken(token).memberNo();

        likeRepositoryJpa.save(Like.of(memberNo, productNo));
    }

    @Transactional
    public void heartOff(Long productNo, String token) {
        Long memberNo = memberService.findMemberByToken(token).memberNo();
        Like like = likeRepositoryJpa.findByMemberNoAndProductNo(memberNo, productNo)
                                  .orElseThrow(() -> new BusinessException(LikeErrorCode.NO_EXIST_LIKE_INFO));

        likeRepositoryJpa.delete(like);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAllLikes(String token, LikeSearchDto likeSearchDto, Pageable pageable) {
        Long memberNo = memberService.findMemberByToken(token).memberNo();
        Page<Like> likesPage = likeRepositoryJpa.findAllByMemberNo(memberNo, pageable);
        List<ProductResponse> productDtos = likesPage.getContent().stream()
                .map(like -> productRepositoryJpa.findById(like.getProductNo()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(ProductResponse::from)
                .toList();

        return new PageImpl<>(productDtos, pageable, productDtos.size());
    }
}
