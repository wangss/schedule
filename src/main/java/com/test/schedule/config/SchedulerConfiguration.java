package com.test.schedule.config;

import com.junyi.cms.schedule.event.ScheduleJobInit;
import com.junyi.cms.schedule.quartz.SpringUtils;
import com.test.schedule.event.ScheduleJobInit;
import com.test.schedule.quartz.SpringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class SchedulerConfiguration {

    @Bean(name = "schedulerFactoryBean")
    public SchedulerFactoryBean getSchedulerFactoryBean() {
        return new SchedulerFactoryBean();
    }

    @Bean(name = "scheduleJobInit")
    public ScheduleJobInit getScheduleJobInit() {
        return new ScheduleJobInit();
    }

    @Bean(name = "springUtils")
    public SpringUtils getSpringUtils() {
        return new SpringUtils();
    }
}
