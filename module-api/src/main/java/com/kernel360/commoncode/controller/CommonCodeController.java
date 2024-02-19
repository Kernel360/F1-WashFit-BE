package com.kernel360.commoncode.controller;

import com.kernel360.commoncode.dto.CommonCodeDto;
import com.kernel360.commoncode.service.CommonCodeService;
import com.kernel360.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.kernel360.commoncode.code.CommonCodeBusinessCode.GET_COMMON_CODE_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/commoncode")
public class CommonCodeController {

    private final CommonCodeService commonCodeService;
    @GetMapping("/{codeName}")
    public ResponseEntity<ApiResponse<List<CommonCodeDto>>> getCommonCode (@PathVariable String codeName){
        List<CommonCodeDto> codes = commonCodeService.getCodes(codeName);

        return ApiResponse.toResponseEntity(GET_COMMON_CODE_SUCCESS, codes);
    }
}
