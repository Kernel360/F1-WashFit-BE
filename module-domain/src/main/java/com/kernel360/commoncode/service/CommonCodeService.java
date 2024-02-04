package com.kernel360.commoncode.service;

import com.kernel360.commoncode.command.CommonCodeCommand;

import java.util.List;

public interface CommonCodeService {

    List<CommonCodeCommand> getCodes(String codeName);
    List<CommonCodeCommand> getCodesWithMapper(String codeName);
}
