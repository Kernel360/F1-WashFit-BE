package com.kernel360.bbs.controller;

import com.kernel360.bbs.code.BBSBusinessCode;
import com.kernel360.bbs.dto.BBSDto;
import com.kernel360.bbs.service.BBSService;
import com.kernel360.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/bbs")
@RequiredArgsConstructor
public class BBSController {
    private final BBSService bbsService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<BBSDto>>> getBBS(
            @RequestParam(value = "sortType", defaultValue = "latest")String sortType,
            @RequestParam(value = "keyword", required = false) String keyword, Pageable pageable
    ){

        return ApiResponse.toResponseEntity(BBSBusinessCode.SUCCESS_REQUEST_GET_BBS, bbsService.getBBSWithCondition(sortType, keyword, pageable));
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<BBSDto>> saveBBS(@RequestBody BBSDto bbsDto){

        return ApiResponse.toResponseEntity(BBSBusinessCode.SUCCESS_REQUEST_GET_BBS);
    }

    @PatchMapping("/modifiy")
    public ResponseEntity<ApiResponse<BBSDto>> modifyBBS(@RequestBody BBSDto bbsDto){

        return ApiResponse.toResponseEntity(BBSBusinessCode.SUCCESS_REQUEST_GET_BBS);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<BBSDto>> deleteBBS(@RequestBody BBSDto bbsDto){
        return ApiResponse.toResponseEntity(BBSBusinessCode.SUCCESS_REQUEST_GET_BBS);
    }
}
