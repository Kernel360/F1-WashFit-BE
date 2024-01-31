package com.kernel360.commoncode.service;

import com.kernel360.commoncode.dto.CommonCodeDto;

import java.util.List;

public interface CommonCodeService {

    List<CommonCodeDto> getCodes(String codeName);
}
