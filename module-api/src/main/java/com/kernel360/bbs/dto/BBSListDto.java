package com.kernel360.bbs.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class BBSListDto {
    Long bbsNo;
    String type;
    String title;
    LocalDateTime createdAt;
    String createdBy;
    Long viewCount;
    Long memberNo;
    String id;
}
