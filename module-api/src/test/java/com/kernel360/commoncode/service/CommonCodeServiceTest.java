package com.kernel360.commoncode.service;

import com.kernel360.commoncode.dto.CommonCodeDto;
import com.kernel360.commoncode.entity.CommonCode;
import com.kernel360.commoncode.repository.CommonCodeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommonCodeServiceTest {
    @Mock
    private CommonCodeRepository commonCodeRepository;

    @InjectMocks
    private CommonCodeService commonCodeService;

    @Test
    @DisplayName("getCodes")
    void 상위코드명을_인수로_받는_공통코드_목록_조회_(){
        /** given **/
        String codeName = "color";

        List<CommonCode> entitys = new ArrayList<>();
        entitys.add(CommonCode.builder()
                              .codeNo(21).codeName("white").upperNo(20).upperName("color").sortOrder(1).isUsed(true).description("흰색").createdAt(LocalDate.parse("2023-12-28")).createdBy("admin").modifiedAt(null).modifiedBy(null).build());
        entitys.add(CommonCode.builder()
                              .codeNo(22).codeName("gray").upperNo(20).upperName("color").sortOrder(5).isUsed(true).description("쥐색").createdAt(LocalDate.parse("2023-12-28")).createdBy("admin").modifiedAt(null).modifiedBy(null).build());
        entitys.add(CommonCode.builder()
                              .codeNo(23).codeName("black").upperNo(20).upperName("color").sortOrder(6).isUsed(true).description("검정색").createdAt(LocalDate.parse("2023-12-28")).createdBy("admin").modifiedAt(null).modifiedBy(null).build());
        entitys.add(CommonCode.builder()
                              .codeNo(24).codeName("red").upperNo(20).upperName("color").sortOrder(4).isUsed(true).description("빨간색").createdAt(LocalDate.parse("2023-12-28")).createdBy("admin").modifiedAt(null).modifiedBy(null).build());
        entitys.add(CommonCode.builder()
                              .codeNo(25).codeName("yellow").upperNo(20).upperName("color").sortOrder(2).isUsed(true).description("노란색").createdAt(LocalDate.parse("2023-12-28")).createdBy("admin").modifiedAt(null).modifiedBy(null).build());
        entitys.add(CommonCode.builder()
                              .codeNo(26).codeName("green").upperNo(20).upperName("color").sortOrder(3).isUsed(true).description("초록색").createdAt(LocalDate.parse("2023-12-28")).createdBy("admin").modifiedAt(null).modifiedBy(null).build());
        entitys.add(CommonCode.builder()
                              .codeNo(27).codeName("blue").upperNo(20).upperName("color").sortOrder(4).isUsed(true).description("파란색").createdAt(LocalDate.parse("2023-12-28")).createdBy("admin").modifiedAt(null).modifiedBy(null).build());
        entitys.add(CommonCode.builder()
                              .codeNo(28).codeName("etc").upperNo(20).upperName("color").sortOrder(7).isUsed(true).description("기타").createdAt(LocalDate.parse("2023-12-28")).createdBy("admin").modifiedAt(null).modifiedBy(null).build());

        System.err.println(entitys);

        /** stub **/
        when(commonCodeRepository.findAllByUpperNameAndIsUsed(anyString(),true)).thenReturn(entitys);


        /** when **/
        List<CommonCodeDto> test = commonCodeService.getCodes(codeName);
        /** then **/
        assertEquals(8,test.size());
    }
}