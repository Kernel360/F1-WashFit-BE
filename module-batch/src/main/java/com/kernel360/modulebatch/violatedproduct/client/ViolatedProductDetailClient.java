package com.kernel360.modulebatch.violatedproduct.client;

import com.kernel360.ecolife.entity.ViolatedProductId;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
public class ViolatedProductDetailClient {
    @Value("${external.ecolife-api.path}")
    private String BASE_PATH;

    @Value("${external.ecolife-api.service-key}")
    private String AUTH_KEY;

    private final RestClient restClient;

    public ViolatedProductDetailClient() {
        this.restClient = RestClient.builder()
                                    .build();
    }

    public String getXmlResponse(ViolatedProductId violatedProductId) {

        return restClient.post().uri(buildUri(violatedProductId))
                         .accept(MediaType.APPLICATION_XML)
                         .acceptCharset(StandardCharsets.UTF_8)
                         .retrieve()
                         .onStatus(HttpStatusCode::is4xxClientError,
                                 ((request, response) -> {
                                     log.error("[ERROR] :: 4XX 에러 발생"
                                             + response.getStatusText());
                                     throw new RuntimeException(
                                             response.getStatusCode()
                                                     + response.getHeaders()
                                                               .toString());
                                 }))
                         .onStatus(HttpStatusCode::is5xxServerError,
                                 (request, response) -> {
                                     log.error("[ERROR] :: 5XX 에러 발생"
                                             + response.getStatusText());
                                     throw new RuntimeException(
                                             response.getStatusCode()
                                                     + response.getHeaders()
                                                               .toString());
                                 })
                         .onStatus(HttpStatusCode::is2xxSuccessful,
                                 ((request, response) -> log.info("[INFO] :: api 요청 성공"
                                         + response.getBody())))
                         .body(String.class);
    }

    public String buildUri(ViolatedProductId violatedProductId) {

        return UriComponentsBuilder.fromHttpUrl(BASE_PATH)
                                   .queryParam("AuthKey", AUTH_KEY)
                                   .queryParam("ServiceName", "violtProductDetail")
                                   .queryParam("prdtMstrNo", violatedProductId.getProductMasterNo())
                                   .queryParam("prdtarmCd", violatedProductId.getProductArmCode())
                                   .build()
                                   .toUriString();
    }
}
