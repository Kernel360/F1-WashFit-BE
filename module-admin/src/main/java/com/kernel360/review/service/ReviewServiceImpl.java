package com.kernel360.review.service;

import com.kernel360.exception.BusinessException;
import com.kernel360.review.code.ReviewErrorCode;
import com.kernel360.review.dto.AdminReviewDto;
import com.kernel360.review.dto.ReviewSearchDto;
import com.kernel360.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<AdminReviewDto> getReviewsByProduct(Long productNo, String sortBy, Pageable pageable) {
        log.info("제품 리뷰 목록 조회 -> product_no {}", productNo);
        // TODO: 유효하지 않은 productNo 인 경우, custom error 보내기

        return reviewRepository.findAllByCondition(ReviewSearchDto.byProductNo(productNo, sortBy), pageable)
                               .map(AdminReviewDto::from);
    }

    @Override
    public Page<AdminReviewDto> getReviewsByMember(Long memberNo, String sortBy, Pageable pageable) {
        log.info("제품 리뷰 목록 조회 -> member_no {}", memberNo);

        return reviewRepository.findAllByCondition(ReviewSearchDto.byMemberNo(memberNo, sortBy), pageable)
                               .map(AdminReviewDto::from);
    }

    @Override
    public Page<AdminReviewDto> getReviews(String sortBy, Pageable pageable) {
        log.info("전체 리뷰 목록 조회 -> order_by {}", sortBy);

        return reviewRepository.findAll(sortBy, pageable)
                               .map(AdminReviewDto::from);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminReviewDto getReview(Long reviewNo) {
        log.info("리뷰 단건 조회 -> review_no {}", reviewNo);

        return AdminReviewDto.from(null);
        //FIXME:: 찬규님 고쳐주세요 (null로 바꿔놨어요  reviewRepository.findByReviewNo(reviewNo) -> null)
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewNo) {
        log.info("리뷰 삭제 -> review_no {}", reviewNo);

        try {
            reviewRepository.deleteById(reviewNo);
        } catch (Exception e) {
            throw new BusinessException(ReviewErrorCode.INVALID_REVIEW_DELETE_REQUEST);
        }
    }

}
