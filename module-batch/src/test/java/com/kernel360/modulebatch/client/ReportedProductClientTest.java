package com.kernel360.modulebatch.client;

import static org.assertj.core.api.Assertions.assertThat;

import com.kernel360.modulebatch.reportedproduct.client.ReportedProductClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
@Disabled
class ReportedProductClientTest {
    private ReportedProductClient client;

    @BeforeEach
    void setUp() {
        client = new ReportedProductClient();
    }

    @Test // 클라이언트 테스트로 이동
    @DisplayName("페이지 번호로 해당 페이지의 신고대상 생활화학제품 목록 xml 문자열을 반환하는데 성공한다.")
    void 페이지_번호로_신고대상_생활화학제품_목록_문자열을_응답받기_테스트() {
        // given
        int pageNumber = 8;

        // when
        String response = client.getXmlResponse(pageNumber);

        // then
        assertThat(response).isNotNull().startsWith("<");

    }

    @Test  // 클라이언트 테스트로 이동
    @DisplayName("API 요청을 위한 URI를 만드는 것에 성공한다")
    public void 페이지_번호로_URL_만들기_테스트() {
        String url = client.buildUri(7);

        assertThat(url).isNotNull()
                       .isEqualTo("https://ecolife.me.go.kr/openapi/ServiceSvl?AuthKey="
                               + System.getenv("API_AUTH_KEY")
                               + "&ServiceName=slfsfcfst02List&PageCount=20&PageNum=7");
    }

}