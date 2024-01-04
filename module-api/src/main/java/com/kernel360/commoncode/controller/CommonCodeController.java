package com.kernel360.commoncode.controller;

import com.kernel360.commoncode.service.CommonCodeService;
import com.kernel360.commoncode.dto.CommonCodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
