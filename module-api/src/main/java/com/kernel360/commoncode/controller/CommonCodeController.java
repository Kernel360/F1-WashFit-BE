package com.kernel360.commoncode.controller;

import com.kernel360.commoncode.command.CommonCodeCommand;
import com.kernel360.commoncode.dto.CommonCodeDto;
import com.kernel360.commoncode.service.CommonCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/commoncode")
public class CommonCodeController {

    private final CommonCodeService commonCodeService;
    @GetMapping("/{codeName}")
    public List<CommonCodeDto> getCommonCode (@PathVariable String codeName){

        List<CommonCodeCommand> command = commonCodeService.getCodes(codeName);
        List<CommonCodeDto> result = command.stream().map(CommonCodeDto::from).toList();

        return result;
    }

    @GetMapping("/mapper/{codeName}")
    public List<CommonCodeDto> getCommonCodeWithMapper (@PathVariable String codeName){

        List<CommonCodeCommand> command = commonCodeService.getCodesWithMapper(codeName);
        List<CommonCodeDto> result = command.stream().map(CommonCodeDto::from).toList();

        return result;
    }

}
