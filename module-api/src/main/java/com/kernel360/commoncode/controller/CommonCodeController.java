package com.kernel360.commoncode.controller;

import com.kernel360.commoncode.service.CommonCodeService;
import com.kernel360.commoncode.dto.CommonCodeDto;
import com.kernel360.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.kernel360.commoncode.code.CommonCodeBusinessCode.GET_COMMON_CODE_SUCCESS;

@RestController
@RequestMapping("/commoncode")
public class CommonCodeController {

    final CommonCodeService commonCodeService;

    @Autowired
    public CommonCodeController(CommonCodeService commonCodeService) {
        this.commonCodeService = commonCodeService;
    }

    @GetMapping("/{codeName}")
    public List<CommonCodeDto> getCommonCode (@PathVariable String codeName){

        return commonCodeService.getCodes(codeName);
    }

    // FIXME :: 아래 메서드는 추후 삭제 예정입니다
    @GetMapping("/test/{codeName}")
    public ResponseEntity<ApiResponse<List<CommonCodeDto>>> getCommonCode_1 (@PathVariable String codeName){
        List<CommonCodeDto> codes = commonCodeService.getCodes(codeName);

        return ApiResponse.toResponseEntity(GET_COMMON_CODE_SUCCESS, codes);
    }
}
