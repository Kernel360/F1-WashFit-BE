package com.kernel360.likes.service;

import com.kernel360.exception.BusinessException;
import com.kernel360.likes.code.LikeErrorCode;
import com.kernel360.likes.entity.Like;
import com.kernel360.likes.repository.LikeRepository;
import com.kernel360.main.code.ProductsErrorCode;
import com.kernel360.member.service.MemberService;
import com.kernel360.product.dto.ProductDto;
import com.kernel360.product.entity.Product;
import com.kernel360.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberService memberService;
    private final ProductRepository productRepository;

    @Transactional
    public void heartOn(Long productNo, String token) {
        Long memberNo = memberService.findMemberByToken(token).memberNo();
        Product product = productRepository.findById(productNo)
                                           .orElseThrow(() -> new BusinessException(ProductsErrorCode.NOT_FOUND_PRODUCT));

        likeRepository.save(Like.of(memberNo, product.getProductNo()));
    }

    @Transactional
    public void heartOff(Long productNo, String token) {
        Long memberNo = memberService.findMemberByToken(token).memberNo();
        Like like = likeRepository.findByMemberNoAndProductNo(memberNo, productNo)
                                  .orElseThrow(() -> new BusinessException(LikeErrorCode.NO_EXIST_LIKE_INFO));

        likeRepository.delete(like);
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> findAllLikes(String token, Pageable pageable) {
        Long memberNo = memberService.findMemberByToken(token).memberNo();

        return likeRepository.findAllByMemberNo(memberNo, pageable)
                .map(like -> productRepository.findById(like.getId())
                        .orElseThrow(() -> new BusinessException(ProductsErrorCode.NOT_FOUND_PRODUCT)))
                .map(ProductDto::from);

    }
}
