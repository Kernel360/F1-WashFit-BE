package com.kernel360.bbs.controller;

import com.kernel360.bbs.code.BBSBusinessCode;
import com.kernel360.bbs.dto.BBSDto;
import com.kernel360.bbs.dto.BBSListDto;
import com.kernel360.bbs.service.BBSService;
import com.kernel360.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bbs")
public class BBSController {
    private final BBSService bbsService;

    @GetMapping("/v1")
    public ResponseEntity<ApiResponse<Page<BBSListDto>>> getBBSv1(
            @RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "keyword", required = false) String keyword,
            Pageable pageable
    ){

        return ApiResponse.toResponseEntity(BBSBusinessCode.SUCCESS_REQUEST_GET_BBS, bbsService.getBBSWithConditionByPage(type, keyword, pageable));
    }

    @GetMapping("/v2")
    public ResponseEntity<ApiResponse<Slice<BBSListDto>>> getBBSv2(
            @RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "keyword", required = false) String keyword,
            Pageable pageable
    ){

        return ApiResponse.toResponseEntity(BBSBusinessCode.SUCCESS_REQUEST_GET_BBS, bbsService.getBBSWithConditionBySlice(type, keyword, pageable));
    }

    @GetMapping("/{bbsNo}")
    public ResponseEntity<ApiResponse<BBSDto>> getBBSView(@PathVariable Long bbsNo){

        return ApiResponse.toResponseEntity(BBSBusinessCode.SUCCESS_REQUEST_GET_BBS_VIEW, bbsService.getBBSView(bbsNo));
    }

    @GetMapping("/reply")
    public ResponseEntity<ApiResponse<Page<BBSDto>>> getBBSReply(@RequestParam Long upperNo, Pageable pageable){

        return ApiResponse.toResponseEntity(BBSBusinessCode.SUCCESS_REQUEST_GET_BBS_VIEW, bbsService.getBBSReply(upperNo, pageable));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<BBSDto>> saveBBS(
            @RequestPart BBSDto bbsDto,
            @RequestHeader("Id") String id,
            @RequestPart(required = false) List<MultipartFile> files
    ){
        bbsService.saveBBS(bbsDto, id, files);

        return ApiResponse.toResponseEntity(BBSBusinessCode.SUCCESS_REQUEST_CREATED_BBS);
    }

    @PatchMapping("")
    public ResponseEntity<ApiResponse<BBSDto>> modifyBBS(
            @RequestPart BBSDto bbsDto,
            @RequestHeader("Id") String id,
            @RequestPart(required = false) List<MultipartFile> files
    ){

        bbsService.saveBBS(bbsDto, id, files);

        return ApiResponse.toResponseEntity(BBSBusinessCode.SUCCESS_REQUEST_MODIFIED_BBS);
    }

    @DeleteMapping("/{bbsNo}")
    public ResponseEntity<ApiResponse<BBSDto>> deleteBBS(@PathVariable Long bbsNo){

        bbsService.deleteBBS(bbsNo);

        return ApiResponse.toResponseEntity(BBSBusinessCode.SUCCESS_REQUEST_DELETE_BBS);
    }
}
