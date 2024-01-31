package com.kernel360.commoncode.service;

import com.kernel360.entity.commoncode.CommonCode;

import java.util.List;

public interface CommonCodeReader {
    List<CommonCode> getCodes(String codeName);

}
