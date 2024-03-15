package com.kernel360.washzonereview.controller;

import com.kernel360.response.ApiResponse;
import com.kernel360.washzonereview.code.WashzoneReviewBusinessCode;
import com.kernel360.washzonereview.dto.WashzoneReviewRequestDto;
import com.kernel360.washzonereview.dto.WashzoneReviewResponseDto;
import com.kernel360.washzonereview.service.WashzoneReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews-washzone")
public class WashzoneReviewController {

    private final WashzoneReviewService washzoneReviewService;

    @GetMapping("/washzone/{washzoneNo}")
    public ResponseEntity<ApiResponse<Page<WashzoneReviewResponseDto>>> getWashzoneReviewsByWashzone(
            @PathVariable Long washzoneNo,
            @RequestParam(name = "sortBy", defaultValue = "washzoneReviewNo", required = false) String sortBy,
            Pageable pageable) {

        return ApiResponse.toResponseEntity(WashzoneReviewBusinessCode.SUCCESS_GET_WASHZONE_REVIEWS, washzoneReviewService.getWashzoneReviewsByWashzone(washzoneNo, sortBy, pageable));
    }

    @GetMapping("/member/{memberNo}")
    public ResponseEntity<ApiResponse<Page<WashzoneReviewResponseDto>>> getWashzoneReviewsByMember(
            @PathVariable Long memberNo,
            @RequestParam(name = "sortBy", defaultValue = "washzoneReviewNo", required = false) String sortBy,
            Pageable pageable) {

        return ApiResponse.toResponseEntity(WashzoneReviewBusinessCode.SUCCESS_GET_WASHZONE_REVIEWS, washzoneReviewService.getWashzoneReviewsByMember(memberNo, sortBy, pageable));
    }

    @GetMapping("/{washzoneReviewNo}")
    public ResponseEntity<ApiResponse<WashzoneReviewResponseDto>> getWashzoneReview(@PathVariable Long washzoneReviewNo) {

        return ApiResponse.toResponseEntity(WashzoneReviewBusinessCode.SUCCESS_GET_WASHZONE_REVIEW, washzoneReviewService.getWashzoneReview(washzoneReviewNo));
    }

    @PostMapping
    public <T> ResponseEntity<ApiResponse<T>> createWashzoneReview(
            @Valid @RequestPart WashzoneReviewRequestDto washzoneReview,
            @RequestPart(required = false) List<MultipartFile> files,
            @RequestHeader("Id") String id) {
        washzoneReviewService.createWashzoneReview(washzoneReview, files, id);

        return ApiResponse.toResponseEntity(WashzoneReviewBusinessCode.SUCCESS_CREATE_WASHZONE_REVIEW);
    }

    @PatchMapping
    public <T> ResponseEntity<ApiResponse<T>> updateWashzoneReview(
            @Valid @RequestPart WashzoneReviewRequestDto washzoneReview,
            @RequestPart(required = false) List<MultipartFile> files,
            @RequestHeader("Id") String id) {
        washzoneReviewService.updateWashzoneReview(washzoneReview, files, id);

        return ApiResponse.toResponseEntity(WashzoneReviewBusinessCode.SUCCESS_UPDATE_WASHZONE_REVIEW);
    }

    @DeleteMapping("/{washzoneReviewNo}")
    public <T> ResponseEntity<ApiResponse<T>> deleteWashzoneReview(
            @PathVariable Long washzoneReviewNo,
            @RequestHeader("Id") String id) {
        washzoneReviewService.deleteWashzoneReview(washzoneReviewNo, id);

        return ApiResponse.toResponseEntity(WashzoneReviewBusinessCode.SUCCESS_DELETE_WASHZONE_REVIEW);
    }
}
