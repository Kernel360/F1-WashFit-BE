package com.kernel360.review.service;

import com.kernel360.exception.BusinessException;
import com.kernel360.review.code.ReviewErrorCode;
import com.kernel360.review.dto.ReviewDto;
import com.kernel360.review.entity.Review;
import com.kernel360.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private static final double MAX_STAR_RATING = 5.0;

    @Transactional(readOnly = true)
    public List<ReviewDto> getReviewsByProduct(Long productNo) {

        return reviewRepository.findAllByProduct_ProductNo(productNo)
                               .stream().map(ReviewDto::from)
                               .toList();
    }

    @Transactional(readOnly = true)
    public ReviewDto getReview(Long reviewNo) {

        return ReviewDto.from(reviewRepository.findByReviewNo(reviewNo));
    }

    @Transactional
    public Review createReview(ReviewDto reviewDto) {
        isValidStarRating(reviewDto.starRating());

        return reviewRepository.save(reviewDto.toEntity());
    }


    @Transactional
    public void updateReview(ReviewDto reviewDto) {
        isValidStarRating(reviewDto.starRating());

        reviewRepository.save(reviewDto.toEntity());
    }

    @Transactional
    public void deleteReview(Long reviewNo) {
        reviewRepository.deleteById(reviewNo);
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
