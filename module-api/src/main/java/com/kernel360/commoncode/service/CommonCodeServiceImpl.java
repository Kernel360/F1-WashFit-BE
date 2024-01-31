package com.kernel360.commoncode.service;

import com.kernel360.commoncode.dto.CommonCodeDto;
import com.kernel360.entity.commoncode.CommonCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonCodeServiceImpl implements CommonCodeService {
    private final CommonCodeReader commonCodeReader;

    public List<CommonCodeDto> getCodes(String codeName) {

        List<CommonCode> codes = commonCodeReader.getCodes(codeName);

        return codes.stream().map(CommonCodeDto::from).toList();
    }
}
