package com.kernel360.review.service;

import com.kernel360.exception.BusinessException;
import com.kernel360.file.entity.File;
import com.kernel360.file.entity.FileReferType;
import com.kernel360.file.repository.FileRepository;
import com.kernel360.review.code.ReviewErrorCode;
import com.kernel360.review.dto.ReviewResponseDto;
import com.kernel360.review.dto.ReviewRequestDto;
import com.kernel360.review.dto.ReviewResponse;
import com.kernel360.review.dto.ReviewSearchDto;
import com.kernel360.review.entity.Review;
import com.kernel360.review.repository.ReviewRepository;
import com.kernel360.utils.file.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final FileRepository fileRepository;
    private final FileUtils fileUtils;

    @Value("${aws.s3.bucket.url}")
    private String bucketUrl;

    private static final double MAX_STAR_RATING = 5.0;
    private static final String REVIEW_DOMAIN = FileReferType.REVIEW.getDomain();
    private static final String REVIEW_CODE = FileReferType.REVIEW.getCode();

    @Transactional(readOnly = true)
    public Page<ReviewResponseDto> getReviewsByProduct(Long productNo, String sortBy, Pageable pageable) {
        log.info("제품 리뷰 목록 조회 -> product_no {}", productNo);

        return reviewRepository.findAllByCondition(ReviewSearchDto.byProductNo(productNo, sortBy), pageable)
                               .map(ReviewResponse::toDto);
    }

    @Transactional(readOnly = true)
    public ReviewResponseDto getReview(Long reviewNo) {
        log.info("리뷰 단건 조회 -> review_no {}", reviewNo);
        ReviewResponse review = reviewRepository.findByReviewNo(reviewNo);

        if (Objects.isNull(review)) {
            throw new BusinessException(ReviewErrorCode.NOT_FOUND_REVIEW);
        }

        return ReviewResponse.toDto(review);
    }

    @Transactional
    public Review createReview(ReviewRequestDto reviewRequestDto, List<MultipartFile> files) {
        isValidStarRating(reviewRequestDto.starRating());

        Review review;

        try {
            review = reviewRepository.saveAndFlush(reviewRequestDto.toEntity());
            log.info("리뷰 등록 -> review_no {}", review.getReviewNo());

            if (Objects.nonNull(files)) {
                uploadFiles(files, reviewRequestDto.productNo(), review.getReviewNo());
            }
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(ReviewErrorCode.INVALID_REVIEW_WRITE_REQUEST);
        }

        return review;
    }

    private void uploadFiles(List<MultipartFile> files, Long productNo, Long reviewNo) {
        files.stream().forEach(file -> {
            String path = String.join("/", REVIEW_DOMAIN, productNo.toString());
            String fileKey = fileUtils.upload(path, file);
            String fileUrl = String.join("/", bucketUrl, fileKey);

            File fileInfo = fileRepository.save(File.of(null, file.getOriginalFilename(), fileKey, fileUrl, REVIEW_CODE, reviewNo));
            log.info("리뷰 파일 등록 -> file_no {}", fileInfo.getFileNo());
        });
    }

    @Transactional
    public void updateReview(ReviewRequestDto reviewRequestDto, List<MultipartFile> files) {
        isValidStarRating(reviewRequestDto.starRating());

        try {
            reviewRepository.saveAndFlush(reviewRequestDto.toEntity());
            log.info("리뷰 수정 -> review_no {}", reviewRequestDto.reviewNo());

            fileRepository.findByReferenceNo(reviewRequestDto.reviewNo())
                          .stream()
                          .forEach(file -> {
                              if (!reviewRequestDto.files().contains(file.getFileUrl())) {
                                  fileUtils.delete(file.getFileKey());
                                  fileRepository.deleteById(file.getFileNo());
                                  log.info("리뷰 파일 삭제 -> file_no {}", file.getFileNo());
                              }
                          });

            if (Objects.nonNull(files)) {
                uploadFiles(files, reviewRequestDto.productNo(), reviewRequestDto.reviewNo());
            }
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(ReviewErrorCode.INVALID_REVIEW_WRITE_REQUEST);
        }
    }

    @Transactional
    public void deleteReview(Long reviewNo) {
        reviewRepository.deleteById(reviewNo);
        log.info("리뷰 삭제 -> review_no {}", reviewNo);

        fileRepository.findByReferenceNo(reviewNo)
                      .stream()
                      .forEach(file -> {
                          fileUtils.delete(file.getFileKey());
                          log.info("리뷰 파일 삭제 -> file_no {}", file.getFileNo());
                      });
        fileRepository.deleteByReferenceNo(reviewNo);
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
