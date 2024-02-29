package com.kernel360.review.service;

import com.kernel360.exception.BusinessException;
import com.kernel360.review.code.ReviewErrorCode;
import com.kernel360.review.dto.ReviewDto;
import com.kernel360.review.entity.Review;
import com.kernel360.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private static final double MAX_STAR_RATING = 5.0;

    @Transactional(readOnly = true)
    public Page<ReviewDto> getReviewsByProduct(Long productNo, Pageable pageable) {
        log.info("제품 리뷰 목록 조회 -> product_no {}", productNo);

        return reviewRepository.findAllByProduct_ProductNo(productNo, pageable)
                               .map(ReviewDto::from);
    }

    @Transactional(readOnly = true)
    public ReviewDto getReview(Long reviewNo) {
        log.info("리뷰 단건 조회 -> review_no {}", reviewNo);

        return ReviewDto.from(reviewRepository.findByReviewNo(reviewNo));
    }

    @Transactional
    public Review createReview(ReviewDto reviewDto) {
        isValidStarRating(reviewDto.starRating());

        Review review = reviewRepository.save(reviewDto.toEntity());
        log.info("리뷰 등록 -> review_no {}", review.getReviewNo());

        return review;
    }


    @Transactional
    public void updateReview(ReviewDto reviewDto) {
        isValidStarRating(reviewDto.starRating());

        reviewRepository.save(reviewDto.toEntity());
        log.info("리뷰 수정 -> review_no {}", reviewDto.reviewNo());
    }

    @Transactional
    public void deleteReview(Long reviewNo) {
        reviewRepository.deleteById(reviewNo);
        log.info("리뷰 삭제 -> review_no {}", reviewNo);
    }

    private static void isValidStarRating(BigDecimal starRating) {
        if (BigDecimal.ZERO.compareTo(starRating) > 0) {
            throw new BusinessException(ReviewErrorCode.INVALID_STAR_RATING_VALUE);
        }

        if (BigDecimal.valueOf(MAX_STAR_RATING).compareTo(starRating) < 0) {
            throw new BusinessException(ReviewErrorCode.INVALID_STAR_RATING_VALUE);
        }
    }
}
