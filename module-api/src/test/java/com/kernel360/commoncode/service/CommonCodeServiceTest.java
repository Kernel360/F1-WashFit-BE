//package com.kernel360.commoncode.service;
//
//import com.kernel360.commoncode.dto.CommonCodeDto;
//import com.kernel360.commoncode.entity.CommonCode;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyBoolean;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class CommonCodeServiceTest {
////    @Mock
////    private CommonCodeRepository commonCodeRepository;
//
//    @InjectMocks
//    private CommonCodeServiceImpl commonCodeServiceImpl;
//
//    @Test
//    @DisplayName("getCodes :: 8개를 지닌 List 조회")
//    void 상위코드명을_인수로_받는_공통코드_목록_조회(){
//        /** given **/
//        String codeName = "color";
//
//        List<CommonCode> entities = new ArrayList<>();
//
//        entities.add(CommonCode.builder().codeNo(21).codeName("white").upperNo(20).upperName("color").sortOrder(1).isUsed(true).description("흰색").build());
//        entities.add(CommonCode.builder().codeNo(22).codeName("gray").upperNo(20).upperName("color").sortOrder(5).isUsed(true).description("쥐색").build());
//        entities.add(CommonCode.builder().codeNo(23).codeName("black").upperNo(20).upperName("color").sortOrder(6).isUsed(true).description("검정색").build());
//        entities.add(CommonCode.builder().codeNo(24).codeName("red").upperNo(20).upperName("color").sortOrder(4).isUsed(true).description("빨간색").build());
//        entities.add(CommonCode.builder().codeNo(25).codeName("yellow").upperNo(20).upperName("color").sortOrder(2).isUsed(true).description("노란색").build());
//        entities.add(CommonCode.builder().codeNo(26).codeName("green").upperNo(20).upperName("color").sortOrder(3).isUsed(true).description("초록색").build());
//        entities.add(CommonCode.builder().codeNo(27).codeName("blue").upperNo(20).upperName("color").sortOrder(4).isUsed(true).description("파란색").build());
//        entities.add(CommonCode.builder().codeNo(28).codeName("etc").upperNo(20).upperName("color").sortOrder(7).isUsed(true).description("기타").build());
//
//        /** stub **/
//       // when(commonCodeRepository.findAllByUpperNameAndIsUsed(anyString(),anyBoolean())).thenReturn(entities);
//
//        /** when **/
//       // List<CommonCodeDto> test = commonCodeServiceImpl.getCodes(codeName);
//
//        /** then **/
//      //  assertEquals(8,test.size());
//    }
//}