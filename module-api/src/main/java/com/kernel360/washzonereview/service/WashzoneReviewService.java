package com.kernel360.washzonereview.service;

import com.kernel360.exception.BusinessException;
import com.kernel360.file.entity.File;
import com.kernel360.file.entity.FileReferType;
import com.kernel360.file.repository.FileRepository;
import com.kernel360.member.repository.MemberRepository;
import com.kernel360.utils.file.FileUtils;
import com.kernel360.washzonereview.code.WashzoneReviewErrorCode;
import com.kernel360.washzonereview.dto.WashzoneReviewRequestDto;
import com.kernel360.washzonereview.dto.WashzoneReviewResponseDto;
import com.kernel360.washzonereview.dto.WashzoneReviewSearchDto;
import com.kernel360.washzonereview.dto.WashzoneReviewSearchResult;
import com.kernel360.washzonereview.entity.WashzoneReview;
import com.kernel360.washzonereview.repository.WashzoneReviewRepository;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class WashzoneReviewService {
    private final WashzoneReviewRepository washzoneReviewRepository;
    private final MemberRepository memberRepository;
    private final FileRepository fileRepository;
    private final FileUtils fileUtils;

    @Value("${aws.s3.bucket.url}")
    private String bucketUrl;

    private static final double MAX_STAR_RATING = 5.0;
    private static final String WASHZONE_REVIEW_DOMAIN = FileReferType.WASHZONE_REVIEW.getDomain();
    private static final String WASHZONE_REVIEW_CODE = FileReferType.WASHZONE_REVIEW.getCode();
    private static final List<String> ALLOWED_FILE_TYPE = Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE);

    @Transactional(readOnly = true)
    public Page<WashzoneReviewResponseDto> getWashzoneReviewsByWashzone(Long washzoneNo, String sortBy, Pageable pageable) {
        log.info("세차창별 리뷰 목록 조회 -> washzone_no {}", washzoneNo);

        return washzoneReviewRepository.findAllByCondition(WashzoneReviewSearchDto.byWashzoneNo(washzoneNo, sortBy), pageable)
                               .map(WashzoneReviewSearchResult::toDto);
    }

    @Transactional(readOnly = true)
    public Page<WashzoneReviewResponseDto> getWashzoneReviewsByMember(Long memberNo, String sortBy, Pageable pageable) {
        log.info("멤버별 세차장 리뷰 목록 조회 -> member_no {}", memberNo);

        return washzoneReviewRepository.findAllByCondition(WashzoneReviewSearchDto.byMemberNo(memberNo, sortBy), pageable)
                               .map(WashzoneReviewSearchResult::toDto);
    }

    @Transactional(readOnly = true)
    public WashzoneReviewResponseDto getWashzoneReview(Long washzoneReviewNo) {
        log.info("세차장 리뷰 단건 조회 -> washzone_review_no {}", washzoneReviewNo);
        WashzoneReviewSearchResult washzoneReview = washzoneReviewRepository.findByWashzoneReviewNo(washzoneReviewNo);

        if (Objects.isNull(washzoneReview)) {
            throw new BusinessException(WashzoneReviewErrorCode.NOT_FOUND_WASHZONE_REVIEW);
        }

        return WashzoneReviewSearchResult.toDto(washzoneReview);
    }

    @Transactional
    public WashzoneReview createWashzoneReview(WashzoneReviewRequestDto requestDto, List<MultipartFile> files, String id) {
        isValidMemberInfo(id, requestDto.memberNo());
        isValidStarRating(requestDto.starRating());
        fileUtils.isValidFileExtension(files, ALLOWED_FILE_TYPE);

        WashzoneReview washzoneReview;

        try {
            washzoneReview = washzoneReviewRepository.saveAndFlush(requestDto.toEntity());
            log.info("세차장 리뷰 등록 -> washzone_review_no {}", washzoneReview.getWashzoneReviewNo());

            if (Objects.nonNull(files)) {
                uploadFiles(files, requestDto.washzoneNo(), washzoneReview.getWashzoneReviewNo());
            }
        } catch (DataIntegrityViolationException e) {
            String msg = e.getMessage().toString();

            if (msg.contains("washzone_review_washzone_no_fkey")) {
                throw new BusinessException(WashzoneReviewErrorCode.NOT_FOUND_WASHZONE_FOR_WASHZONE_REVIEW_CREATION);
            }

            if (msg.contains("washzone_review_member_no_fkey")) {
                throw new BusinessException(WashzoneReviewErrorCode.NOT_FOUND_MEMBER_FOR_WASHZONE_REVIEW_CREATION);
            }

            throw new BusinessException(WashzoneReviewErrorCode.DUPLICATE_WASHZONE_REVIEW_EXISTS);
        }

        return washzoneReview;
    }

    private void uploadFiles(List<MultipartFile> files, Long washzoneNo, Long washzoneReviewNo) {
        files.stream().forEach(file -> {
            String path = String.join("/", WashzoneReviewService.WASHZONE_REVIEW_DOMAIN, washzoneNo.toString());
            String fileKey = fileUtils.upload(path, file);
            String fileUrl = String.join("/", bucketUrl, fileKey);

            File fileInfo = fileRepository.save(File.of(null, file.getOriginalFilename(), fileKey, fileUrl, WashzoneReviewService.WASHZONE_REVIEW_CODE, washzoneReviewNo));
            log.info("세차장 리뷰 파일 등록 -> file_no {}", fileInfo.getFileNo());
        });
    }

    @Transactional
    public void updateWashzoneReview(WashzoneReviewRequestDto requestDto, List<MultipartFile> files, String id) {
        WashzoneReview washzoneReview = isVisibleStatus(requestDto.washzoneReviewNo());
        isValidMemberInfo(id, washzoneReview.getMember().getMemberNo());
        isValidStarRating(requestDto.starRating());
        fileUtils.isValidFileExtension(files, ALLOWED_FILE_TYPE);

        long washzoneNo = washzoneReview.getWashzone().getWashZoneNo();

        try {
            washzoneReviewRepository.saveAndFlush(requestDto.toEntityForUpdate());
            log.info("세차장 리뷰 수정 -> washzone_review_no {}", requestDto.washzoneReviewNo());

            fileRepository.findByReferenceTypeAndReferenceNo(WashzoneReviewService.WASHZONE_REVIEW_CODE, requestDto.washzoneReviewNo())
                          .stream()
                          .forEach(file -> {
                              if (!requestDto.files().contains(file.getFileUrl())) {
                                  fileUtils.delete(file.getFileKey());
                                  fileRepository.deleteById(file.getFileNo());
                                  log.info("세차장 리뷰 파일 삭제 -> file_no {}", file.getFileNo());
                              }
                          });

            if (Objects.nonNull(files)) {
                uploadFiles(files, washzoneNo, requestDto.washzoneReviewNo());
            }
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(WashzoneReviewErrorCode.DUPLICATE_WASHZONE_REVIEW_EXISTS);
        }
    }

    @Transactional
    public void deleteWashzoneReview(Long washzoneReviewNo, String id) {
        WashzoneReview washzoneReview = isVisibleStatus(washzoneReviewNo);
        isValidMemberInfo(id, washzoneReview.getMember().getMemberNo());

        washzoneReviewRepository.deleteById(washzoneReviewNo);
        log.info("세차장 리뷰 삭제 -> washzone_review_no {}", washzoneReviewNo);

        fileRepository.findByReferenceTypeAndReferenceNo(WashzoneReviewService.WASHZONE_REVIEW_CODE, washzoneReviewNo)
                      .stream()
                      .forEach(file -> {
                          fileUtils.delete(file.getFileKey());
                          log.info("세차장 리뷰 파일 삭제 -> file_no {}", file.getFileNo());
                      });
        fileRepository.deleteByReferenceTypeAndReferenceNo(WashzoneReviewService.WASHZONE_REVIEW_CODE, washzoneReviewNo);
    }

    private WashzoneReview isVisibleStatus(Long washzoneReviewNo) {
        Optional<WashzoneReview> washzoneReview = washzoneReviewRepository.findByWashzoneReviewNoAndIsVisibleTrue(washzoneReviewNo);

        if (washzoneReview.isEmpty()) {
            throw new BusinessException(WashzoneReviewErrorCode.NOT_FOUND_WASHZONE_REVIEW);
        }

        return washzoneReview.get();
    }

    private void isValidMemberInfo(String id, Long memberNo) {
        memberRepository.findOneByIdAndMemberNo(id, memberNo)
                        .orElseThrow(() -> new BusinessException(WashzoneReviewErrorCode.MISMATCHED_MEMBER_NO_AND_ID));
    }

    private void isValidStarRating(BigDecimal starRating) {
        if (BigDecimal.ZERO.compareTo(starRating) > 0) {
            throw new BusinessException(WashzoneReviewErrorCode.INVALID_STAR_RATING_VALUE);
        }

        if (BigDecimal.valueOf(MAX_STAR_RATING).compareTo(starRating) < 0) {
            throw new BusinessException(WashzoneReviewErrorCode.INVALID_STAR_RATING_VALUE);
        }
    }
}
