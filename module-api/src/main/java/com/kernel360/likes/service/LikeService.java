package com.kernel360.likes.service;

import com.kernel360.exception.BusinessException;
import com.kernel360.likes.code.LikeErrorCode;
import com.kernel360.likes.dto.LikeSearchDto;
import com.kernel360.likes.entity.Like;
import com.kernel360.likes.repository.LikeRepository;
import com.kernel360.member.service.MemberService;
import com.kernel360.product.dto.ProductResponse;
import com.kernel360.product.repository.ProductRepository;
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

    private final LikeRepository likeRepository;
    private final MemberService memberService;
    private final ProductRepository productRepository;

    @Transactional
    public void heartOn(Long productNo, String token) {
        Long memberNo = memberService.findMemberByToken(token).memberNo();

        likeRepository.save(Like.of(memberNo, productNo));
    }

    @Transactional
    public void heartOff(Long productNo, String token) {
        Long memberNo = memberService.findMemberByToken(token).memberNo();
        Like like = likeRepository.findByMemberNoAndProductNo(memberNo, productNo)
                                  .orElseThrow(() -> new BusinessException(LikeErrorCode.NO_EXIST_LIKE_INFO));

        likeRepository.delete(like);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAllLikes(String token, LikeSearchDto likeSearchDto, Pageable pageable) {
        Long memberNo = memberService.findMemberByToken(token).memberNo();
        Page<Like> likesPage = likeRepository.findAllByMemberNo(memberNo, pageable);
        List<ProductResponse> productDtos = likesPage.getContent().stream()
                .map(like -> productRepository.findById(like.getProductNo()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(ProductResponse::from)
                .toList();

        return new PageImpl<>(productDtos, pageable, productDtos.size());
    }
}
