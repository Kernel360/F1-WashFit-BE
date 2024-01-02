package com.kernel360.test.api;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReportableApiDetailExplorer {
    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("https://ecolife.me.go.kr/openapi/ServiceSvl");
        urlBuilder.append("?").append(URLEncoder.encode("AuthKey", "UTF-8"))
                  .append("=F985H62E9X1Q9X93Z81969N12L98SID5");
        urlBuilder.append("&").append(URLEncoder.encode("ServiceName", "UTF-8"))
                  .append("=").append(URLEncoder.encode("slfsfcfst02Detail", "UTF-8"));

        urlBuilder.append("&").append(URLEncoder.encode("mstId", "UTF-8"))
                  .append("=").append(URLEncoder.encode("NLC_D4FF21C37", "UTF-8"));

        urlBuilder.append("&").append(URLEncoder.encode("estNo", "UTF-8"))
                  .append("=").append(URLEncoder.encode("6", "UTF-8"));
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        log.info("응답 코드: {}", conn.getResponseCode());

        try (FileWriter fw = new FileWriter("reportable_product_detail.xml")) {
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(
                    conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300
                            ? conn.getInputStream()
                            : conn.getErrorStream()))) {

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                fw.write(sb.toString());
            }

        }
    }
}
