package com.kernel360.likes.controller;


import com.kernel360.likes.code.LikeBusinessCode;
import com.kernel360.likes.dto.LikeSearchDto;
import com.kernel360.likes.entity.Like;
import com.kernel360.likes.service.LikeService;
import com.kernel360.main.controller.Sort;
import com.kernel360.product.dto.ProductResponse;
import com.kernel360.product.service.ProductService;
import com.kernel360.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;
    private final ProductService productService;

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getAllLikes(
            @RequestHeader(value = "Authorization", required = true ) String token,
            @RequestParam(name = "sortType", defaultValue = "viewCnt-order") Sort sortType,
            @RequestParam(value = "keyword", required = false) String keyword, Pageable pageable){
        Page<ProductResponse> likes = likeService.findAllLikes(token, LikeSearchDto.of(token, keyword, sortType), pageable);

        return ApiResponse.toResponseEntity(LikeBusinessCode.LIKE_LIST_SEARCH_SUCCESS, likes);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Like>> likeOn(@RequestParam("productNo") Long productNo,
                                                    @RequestHeader("Authorization") String token){
        likeService.heartOn(productNo, token);

        return ApiResponse.toResponseEntity(LikeBusinessCode.LIKE_ON_SUCCESS);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Like>> likeOff(@RequestParam("productNo") Long productNo,
                                                    @RequestHeader("Authorization") String token) {
        likeService.heartOff(productNo, token);

        return ApiResponse.toResponseEntity(LikeBusinessCode.LIKE_OFF_SUCCESS);
    }
}
