package com.kernel360.review.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.kernel360.common.ControllerTest;
import com.kernel360.member.dto.MemberDto;
import com.kernel360.product.dto.ProductDetailDto;
import com.kernel360.review.dto.ReviewResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.kernel360.common.utils.RestDocumentUtils.getDocumentRequest;
import static com.kernel360.common.utils.RestDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReviewControllerTest extends ControllerTest {

    @Test
    void 리뷰_단건조회() throws Exception {
        // given
        ReviewResponseDto response = ReviewResponseDto.of(
                302L,
                ProductDetailDto.of(
                        63418L,
                        "파이널 폴리쉬",
                        null,
                        "(주)오토왁스",
                        "코팅제품",
                        "광택코팅제"
                ),
                MemberDto.of(
                        251L,
                        "wa*******",
                        0,
                        0
                ),
                BigDecimal.valueOf(1.5),
                "10306 수정 타이틀",
                "10306 수정 contents",
                LocalDateTime.parse("2024-03-05T00:00:00"),
                "admin",
                LocalDateTime.parse("2024-03-06T00:00:00"),
                "admin",
                Arrays.asList()
        );

        Long reviewNo = 302L;
        given(reviewService.getReview(reviewNo)).willReturn(response);


        // when
        ResultActions result = this.mockMvc.perform(get("/reviews/{reviewNo}", reviewNo));


        // then
        result.andExpect(status().isOk())
              .andDo(document(
                      "reviews/get-review",
                      getDocumentRequest(),
                      getDocumentResponse(),
                      resource(ResourceSnippetParameters
                              .builder()
                              .tag("Review API")
                              .summary("리뷰 단건 조회")
                              .pathParameters(
                                      parameterWithName("reviewNo").description("리뷰번호")
                              )
                              .responseFields(
                                      fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태"),
                                      fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                      fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                      fieldWithPath("value").type(JsonFieldType.OBJECT).description("응답 데이터 리스트"),
                                      fieldWithPath("value.reviewNo").type(JsonFieldType.NUMBER).description("리뷰번호"),
                                      fieldWithPath("value.starRating").type(JsonFieldType.NUMBER).description("별점"),
                                      fieldWithPath("value.title").type(JsonFieldType.STRING).description("제목"),
                                      fieldWithPath("value.contents").type(JsonFieldType.STRING).description("내용"),
                                      fieldWithPath("value.createdAt").type(JsonFieldType.STRING).description("생성일시"),
                                      fieldWithPath("value.createdBy").type(JsonFieldType.STRING).description("생성자"),
                                      fieldWithPath("value.modifiedAt").type(JsonFieldType.STRING).description("수정일시").optional(),
                                      fieldWithPath("value.modifiedBy").type(JsonFieldType.STRING).description("수정자").optional(),
                                      fieldWithPath("value.files").type(JsonFieldType.ARRAY).description("파일 URL 리스트"),
                                      fieldWithPath("value.product").type(JsonFieldType.OBJECT).description("제품 정보"),
                                      fieldWithPath("value.product.productNo").type(JsonFieldType.NUMBER).description("제품번호"),
                                      fieldWithPath("value.product.productName").type(JsonFieldType.STRING).description("제품명"),
                                      fieldWithPath("value.product.imageSource").type(JsonFieldType.VARIES).description("이미지 (문자열 또는 null)"),
                                      fieldWithPath("value.product.companyName").type(JsonFieldType.STRING).description("회사명"),
                                      fieldWithPath("value.product.upperItem").type(JsonFieldType.STRING).description("상위 타입"),
                                      fieldWithPath("value.product.item").type(JsonFieldType.STRING).description("타입"),
                                      fieldWithPath("value.product.barcode").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.reportNumber").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.safetyStatus").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.viewCount").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.productType").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.issuedDate").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.safetyInspectionStandard").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.propose").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.weight").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.usage").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.usagePrecaution").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.firstAid").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.mainSubstance").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.allergicSubstance").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.otherSubstance").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.preservative").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.surfactant").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.fluorescentWhitening").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.manufactureType").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.manufactureMethod").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.manufactureNation").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.violationInfo").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.createdAt").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.createdBy").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.modifiedAt").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.product.modifiedBy").type(JsonFieldType.NULL).description("타입"),
                                      fieldWithPath("value.member").type(JsonFieldType.OBJECT).description("회원 정보"),
                                      fieldWithPath("value.member.memberNo").type(JsonFieldType.NUMBER).description("회원번호"),
                                      fieldWithPath("value.member.id").type(JsonFieldType.STRING).description("ID"),
                                      fieldWithPath("value.member.age").type(JsonFieldType.STRING).description("나이"),
                                      fieldWithPath("value.member.gender").type(JsonFieldType.STRING).description("성별"),
                                      fieldWithPath("value.member.email").type(JsonFieldType.NULL).description("성별"),
                                      fieldWithPath("value.member.password").type(JsonFieldType.NULL).description("성별"),
                                      fieldWithPath("value.member.createdAt").type(JsonFieldType.NULL).description("성별"),
                                      fieldWithPath("value.member.createdBy").type(JsonFieldType.NULL).description("성별"),
                                      fieldWithPath("value.member.modifiedAt").type(JsonFieldType.NULL).description("성별"),
                                      fieldWithPath("value.member.modifiedBy").type(JsonFieldType.NULL).description("성별"),
                                      fieldWithPath("value.member.jwtToken").type(JsonFieldType.NULL).description("성별")
                              )
                              .requestSchema(Schema.schema("리뷰 단건 조회"))
                              .responseSchema(Schema.schema("리뷰 단건 조회"))
                              .build()
                      )));
    }

    @Test
    void 리뷰등록() throws Exception {
        // given
        String reviewJson = "{\"productNo\": 63354, \"memberNo\": 201, \"starRating\": 1.0, \"title\": \"제목\", \"contents\": \"내용\"}";

        MockMultipartFile review = new MockMultipartFile("review", "review.json", "application/json", reviewJson.getBytes());
        MockMultipartFile file1 = new MockMultipartFile("files", "flower-1.png", "image/png", "<<flower-1.png data>>".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("files", "space-1.jpg", "image/jpeg", "<<space-1.jpg data>>".getBytes());


        // when
        ResultActions result = this.mockMvc.perform(multipart("/reviews")
                .file(review)
                .file(file1)
                .file(file2)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("Id", "member01")
        );


        // then
        result.andExpect(status().isOk())
              .andDo(document(
                      "reviews/create-review",
                      getDocumentRequest(),
                      getDocumentResponse(),
                      requestParts(
                              partWithName("files").description("파일 리스트"),
                              partWithName("review").description("리뷰 데이터")
                      ),
                      resource(ResourceSnippetParameters
                              .builder()
                              .tag("Review API")
                              .summary("리뷰 등록")
                              .responseFields(
                                      fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태"),
                                      fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                      fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                      fieldWithPath("value").type(JsonFieldType.NULL).description("응답 데이터")
                              )
                              .requestSchema(Schema.schema("리뷰 등록"))
                              .responseSchema(Schema.schema("리뷰 등록"))
                              .build()
                      )));
    }
}