package com.kernel360.bbs.controller;

import com.kernel360.bbs.code.BBSBusinessCode;
import com.kernel360.bbs.dto.BBSDto;
import com.kernel360.bbs.dto.BBSListDto;
import com.kernel360.bbs.service.BBSService;
import com.kernel360.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BBSController {
    private final BBSService bbsService;

    @GetMapping("/bbs")
    public ResponseEntity<ApiResponse<Page<BBSListDto>>> getBBS(
            @RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "keyword", required = false) String keyword, Pageable pageable
    ){
        Page<BBSListDto> result = bbsService.getBBSWithCondition(type, keyword, pageable);

        return ApiResponse.toResponseEntity(BBSBusinessCode.SUCCESS_REQUEST_GET_BBS, result);
    }

    @GetMapping("/bbs/{bbsNo}")
    public ResponseEntity<ApiResponse<BBSDto>> getBBSView(@PathVariable Long bbsNo){

        return ApiResponse.toResponseEntity(BBSBusinessCode.SUCCESS_REQUEST_GET_BBS_VIEW, bbsService.getBBSView(bbsNo));
    }

    @GetMapping("/bbs/reply")
    public ResponseEntity<ApiResponse<Page<BBSDto>>> getBBSReply(@RequestParam Long upperNo, Pageable pageable){

        return ApiResponse.toResponseEntity(BBSBusinessCode.SUCCESS_REQUEST_GET_BBS_VIEW, bbsService.getBBSReply(upperNo, pageable));
    }

    @PostMapping("/bbs")
    public ResponseEntity<ApiResponse<BBSDto>> saveBBS(@RequestBody BBSDto bbsDto, @RequestHeader("Id") String id){

        bbsService.saveBBS(bbsDto, id);

        return ApiResponse.toResponseEntity(BBSBusinessCode.SUCCESS_REQUEST_CREATED_BBS);
    }

    @PatchMapping("/bbs")
    public ResponseEntity<ApiResponse<BBSDto>> modifyBBS(@RequestBody BBSDto bbsDto, @RequestHeader("Id") String id){

        bbsService.saveBBS(bbsDto, id);

        return ApiResponse.toResponseEntity(BBSBusinessCode.SUCCESS_REQUEST_MODIFIED_BBS);
    }

    @DeleteMapping("/bbs")
    public ResponseEntity<ApiResponse<BBSDto>> deleteBBS(@RequestParam Long bbsNo){

        bbsService.deleteBBS(bbsNo);

        return ApiResponse.toResponseEntity(BBSBusinessCode.SUCCESS_REQUEST_DELETE_BBS);
    }
}
