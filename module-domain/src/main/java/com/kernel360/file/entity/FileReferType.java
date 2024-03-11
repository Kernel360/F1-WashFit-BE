package com.kernel360.file.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FileReferType {
    REVIEW("review", "RV"),
    WASHZONE_REVIEW("washzone-review", "WZRV");

    private final String domain;
    private final String code;

    public String getDomain() {
        return domain;
    }

    public String getCode() {
        return code;
    }
}
