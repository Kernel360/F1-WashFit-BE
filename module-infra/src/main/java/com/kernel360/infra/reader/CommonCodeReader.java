package com.kernel360.infra.reader;

import com.kernel360.entity.commoncode.CommonCode;

import java.util.List;

public interface CommonCodeReader {
    List<CommonCode> getCodes(String codeName);

    List<CommonCode> getCodesWithMapper(String codeName);
}
