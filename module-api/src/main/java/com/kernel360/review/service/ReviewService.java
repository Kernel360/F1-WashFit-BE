package com.kernel360.review.service;

import com.kernel360.exception.BusinessException;
import com.kernel360.review.code.ReviewErrorCode;
import com.kernel360.review.dto.ReviewDto;
import com.kernel360.review.dto.ReviewSearchDto;
import com.kernel360.review.entity.Review;
import com.kernel360.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private static final double MAX_STAR_RATING = 5.0;

    @Transactional(readOnly = true)
    public Page<ReviewDto> getReviewsByProduct(Long productNo, String sortBy, Pageable pageable) {
        log.info("제품 리뷰 목록 조회 -> product_no {}", productNo);
        // TODO: 유효하지 않은 productNo 인 경우, custom error 보내기

        return reviewRepository.findAllByCondition(ReviewSearchDto.byProductNo(productNo, sortBy), pageable)
                               .map(ReviewDto::from);
    }

    @Transactional(readOnly = true)
    public ReviewDto getReview(Long reviewNo) {
        Review review = reviewRepository.findByReviewNo(reviewNo);

        if (Objects.isNull(review)) {
            throw new BusinessException(ReviewErrorCode.NOT_FOUND_REVIEW);
        }

        log.info("리뷰 단건 조회 -> review_no {}", reviewNo);
        return ReviewDto.from(review);
    }

    @Transactional
    public Review createReview(ReviewDto reviewDto) {
        isValidStarRating(reviewDto.starRating());

        Review review;

        try {
            review = reviewRepository.saveAndFlush(reviewDto.toEntity());
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(ReviewErrorCode.INVALID_REVIEW_WRITE_REQUEST);
        }

        log.info("리뷰 등록 -> review_no {}", review.getReviewNo());
        return review;
    }


    @Transactional
    public void updateReview(ReviewDto reviewDto) {
        isValidStarRating(reviewDto.starRating());

        try {
            reviewRepository.saveAndFlush(reviewDto.toEntity());
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(ReviewErrorCode.INVALID_REVIEW_WRITE_REQUEST);
        }

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
