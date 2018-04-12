package com.test.schedule.quartz;

import com.junyi.cms.schedule.model.ScheduleJob;
import com.test.schedule.model.ScheduleJob;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 同步的,上一次执行完，下一次才执行，哪怕到点
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SyncJobFactory extends QuartzJobBean {
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        TaskUtils.invokeMethod(scheduleJob);
    }
}
