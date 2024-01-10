package com.kernel360.modulebatch.job;

import static org.mockito.Mockito.mock;

import com.kernel360.modulebatch.service.ReportedProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


@SpringBatchTest
@SpringJUnitConfig(classes = {ReportedProductApiJobConfig.class, TestBatchConfiguration.class})
class ReportedProductApiJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @MockBean
    private ReportedProductService service;

    @BeforeEach
    void setUp(){
        service = mock(ReportedProductService.class);
    }


    @Test
    @DisplayName("신고대상 생활화학 제품 fetch Job 테스트")
    void fetch_Job_테스트(@Autowired Job job) throws Exception {

    }

    @Test
    @DisplayName("신고대상 생활화학 제품 Step 테스트")
    void fetch_Step_테스트() {

    }

    //    List<ReportedProductDto> items = Arrays.asList(
//            ReportedProductDto.of("testMasterId_01",
//                    "testName01",
//                    "1234",
//                    "코팅제",
//                    7,
//                    "20231011",
//                    "테스트회사1"),
//            ReportedProductDto.of("testMasterId_02",
//                    "testName03",
//                    "1235",
//                    "광택제",
//                    7,
//                    "20221010",
//                    "테스트회사2")
//
//    );
}