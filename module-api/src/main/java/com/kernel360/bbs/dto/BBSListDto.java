package com.kernel360.bbs.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class BBSListDto {
    Long bbsNo;
    String type;
    String title;
    LocalDate createdAt;
    String createdBy;
    Long viewCount;
    Long memberNo;
    String id;
}
