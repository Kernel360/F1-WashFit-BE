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
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommonCodeServiceTest {
    @Mock
    private CommonCodeRepository commonCodeRepository;

    @InjectMocks
    private CommonCodeService commonCodeService;

    @Test
    @DisplayName("getCodes :: 8개를 지닌 List 조회")
    void 상위코드명을_인수로_받는_공통코드_목록_조회(){
        /** given **/
        String codeName = "color";

        List<CommonCode> entities = new ArrayList<>();

        entities.add(new CommonCode (21,"white",20,"color",1,true,"흰색","2023-12-28","admin"));
        entities.add(new CommonCode (22,"gray",20,"color",5,true,"쥐색","2023-12-28","admin"));
        entities.add(new CommonCode (23,"black",20,"color",6,true,"검정색","2023-12-28","admin"));
        entities.add(new CommonCode (24,"red",20,"color",4,true,"빨간색","2023-12-28","admin"));
        entities.add(new CommonCode (25,"yellow",20,"color",2,true,"노란색","2023-12-28","admin"));
        entities.add(new CommonCode (26,"green",20,"color",3,true,"초록색","2023-12-28","admin"));
        entities.add(new CommonCode (27,"blue",20,"color",4,true,"파란색","2023-12-28","admin"));
        entities.add(new CommonCode (28,"etc",20,"color",7,true,"기타","2023-12-28","admin"));

        /** stub **/
        when(commonCodeRepository.findAllByUpperNameAndIsUsed(anyString(),anyBoolean())).thenReturn(entities);

        /** when **/
        List<CommonCodeDto> test = commonCodeService.getCodes(codeName);

        /** then **/
        assertEquals(8,test.size());
    }
}