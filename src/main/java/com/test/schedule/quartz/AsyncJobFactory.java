package com.test.schedule.quartz;

import com.junyi.cms.schedule.model.ScheduleJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 异步的
 */
public class AsyncJobFactory extends QuartzJobBean {

    /* 日志对象 */
    private static final Logger logger = LoggerFactory.getLogger(AsyncJobFactory.class);
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        TaskUtils.invokeMethod(scheduleJob);
    }
}
