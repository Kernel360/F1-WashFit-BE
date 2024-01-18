package com.kernel360.modulebatch.Quartz;

import lombok.RequiredArgsConstructor;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
@RequiredArgsConstructor
public class QuartzConfig {

    private final ApplicationContext applicationContext;

    //-- JobDetail setting --//
    @Bean
    public JobDetail fetchReportedProductWithBrand() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("fetchReportedProductFromBrandJob",
                applicationContext.getBean("fetchReportedProductFromBrandJob"));
        jobDataMap.put("fetchReportedProductDetailJob",
                applicationContext.getBean("fetchReportedProductDetailJob"));

        jobDataMap.put("applicationContext", applicationContext);

        return JobBuilder.newJob(FetchReportedProductWithBrandSchedule.class)
                         .withIdentity("fetchReportedProductWithBrand")
                         .setJobData(jobDataMap)
                         .storeDurably()
                         .build();
    }

    @Bean
    public JobDetail importProductFromReportedProduct() {
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("importProductFromReportedProductJob",
                applicationContext.getBean("importProductFromReportedProductJob"));

        jobDataMap.put("applicationContext", applicationContext);

        return JobBuilder.newJob(ImportProductFromReportedProductSchedule.class)
                         .withIdentity("importProductFromReportedProductJob")
                         .setJobData(jobDataMap)
                         .storeDurably()
                         .build();
    }

    //-- Trigger --//
    @Bean
    public Trigger fetchReportedProductWithBrandExecuteTrigger(
            @Qualifier("fetchReportedProductWithBrand") JobDetail jobDetail) {
        String cronExpression = "0 0 1 ? * FRI *";  // 매주 금요일 새벽 1시

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression)
                                                                 .withMisfireHandlingInstructionDoNothing();

        return TriggerBuilder.newTrigger()
                             .forJob(jobDetail)
                             .withIdentity("fetchReportedProductWithBrandExecuteTrigger")
                             .withSchedule(scheduleBuilder)
                             .build();
    }

    @Bean
    public Trigger importProductFromReportedProductExecuteTrigger(
            @Qualifier("importProductFromReportedProduct") JobDetail jobDetail) {
        String cronExpression = "0 0 2 ? * FRI *";  // 매주 금요일 새벽 2시

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression)
                                                                 .withMisfireHandlingInstructionDoNothing();

        return TriggerBuilder.newTrigger()
                             .forJob(jobDetail)
                             .withIdentity("importProductFromReportedProductJobExecuteTrigger")
                             .withSchedule(scheduleBuilder)
                             .build();
    }

    //-- Scheduler --//

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobDetail fetchReportedProductWithBrand,
                                                     Trigger fetchReportedProductWithBrandExecuteTrigger,
                                                     JobDetail importProductFromReportedProduct,
                                                     Trigger importProductFromReportedProductExecuteTrigger) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobDetails(fetchReportedProductWithBrand, importProductFromReportedProduct);
        schedulerFactoryBean.setTriggers(fetchReportedProductWithBrandExecuteTrigger,
                importProductFromReportedProductExecuteTrigger);

        return schedulerFactoryBean;
    }
}
