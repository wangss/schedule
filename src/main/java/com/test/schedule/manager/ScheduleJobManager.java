package com.test.schedule.manager;

import com.junyi.cms.common.exception.ScheduleException;
import com.junyi.cms.schedule.model.ScheduleJob;
import com.junyi.cms.schedule.quartz.AsyncJobFactory;
import com.junyi.cms.schedule.quartz.SyncJobFactory;
import com.test.schedule.quartz.AsyncJobFactory;
import org.quartz.*;
import org.slf4j.LoggerFactory;

public class ScheduleJobManager {
	/** 日志对象 */
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ScheduleJobManager.class);


	/**
	 * 获取触发器key
	 * @param jobName
	 * @param jobGroup
	 * @return
	 */
	public static TriggerKey getTriggerKey(String jobName, String jobGroup) {
		return TriggerKey.triggerKey(jobName, jobGroup);
	}

	/**
	 * 获取表达式触发器
	 *
	 * @param scheduler the scheduler
	 * @param jobName the job name
	 * @param jobGroup the job group
	 * @return cron trigger
	 */
	public static CronTrigger getCronTrigger(Scheduler scheduler,
											 String jobName,
											 String jobGroup) throws ScheduleException {

		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
			return (CronTrigger) scheduler.getTrigger(triggerKey);

		} catch (SchedulerException e) {
			logger.error("getCronTrigger error", e);
			throw new ScheduleException("getCronTrigger error");
		}
	}


	/**
	 * 创建定时任务
	 *
	 * @param scheduler the scheduler
	 */
	public static void createScheduleJob(Scheduler scheduler,
										 ScheduleJob job) throws ScheduleException {

		String jobName = job.getJobName();
		String jobGroup = job.getJobGroup();
		String cronExpression = job.getCronExpression();

		//同步或异步
		Class<? extends Job> jobClazz = job.getIsConcurrent() == 1 ?
				AsyncJobFactory.class :
				SyncJobFactory.class;

		//构建job信息
		JobDetail jobDetail = JobBuilder
				.newJob(jobClazz)
				.withIdentity(jobName, jobGroup)
				.build();

		jobDetail.getJobDataMap().put("scheduleJob", job);

		//表达式调度构建器
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

		//按新的cronExpression表达式构建一个新的trigger
		CronTrigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity(jobName, jobGroup)
				.withSchedule(scheduleBuilder)
				.build();

		String jobTrigger = trigger.getKey().getName();

		try {
			scheduler.scheduleJob(jobDetail, trigger);

		} catch (SchedulerException e) {

			logger.error("createScheduleJob error", e);
			throw new ScheduleException("createScheduleJob error");
		}
	}

	/**
	 * 运行一次任务
	 *
	 * @param scheduler
	 * @param jobName
	 * @param jobGroup
	 */
	public static void runOnce(Scheduler scheduler,
							   String jobName,
							   String jobGroup) throws ScheduleException {

		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		try {
			scheduler.triggerJob(jobKey);
		} catch (SchedulerException e) {
			logger.error("runOnce error", e);
			throw new ScheduleException("runOnce error");
		}
	}

	/**
	 * 暂停任务
	 *
	 * @param scheduler
	 * @param jobName
	 * @param jobGroup
	 */
	public static void pauseJob(Scheduler scheduler,
								String jobName,
								String jobGroup) throws ScheduleException {

		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		try {
			scheduler.pauseJob(jobKey);

		} catch (SchedulerException e) {
			logger.error("pauseJob error", e);
			throw new ScheduleException("runOnce error");

		}
	}

	/**
	 * 恢复任务
	 *
	 * @param scheduler
	 * @param jobName
	 * @param jobGroup
	 */
	public static void resumeJob(Scheduler scheduler,
								 String jobName,
								 String jobGroup) throws ScheduleException {

		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		try {
			scheduler.resumeJob(jobKey);

		} catch (SchedulerException e) {
			logger.error("resumeJob error", e);
			throw new ScheduleException("resumeJob error");
		}
	}

	/**
	 * 获取jobKey
	 *
	 * @param jobName the job name
	 * @param jobGroup the job group
	 * @return the job key
	 */
	public static JobKey getJobKey(String jobName, String jobGroup) {

		return JobKey.jobKey(jobName, jobGroup);
	}

	/**
	 * 更新定时任务
	 *
	 * @param scheduler the scheduler
	 * @param scheduleJob the schedule job
	 */
	public static void updateScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) throws ScheduleException {

		updateScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup(),
				scheduleJob.getCronExpression());

	}

	/**
	 * 更新定时任务
	 *
	 * @param scheduler the scheduler
	 * @param jobName the job name
	 * @param jobGroup the job group
	 * @param cronExpression the cron expression
	 */
	public static void updateScheduleJob(Scheduler scheduler, String jobName, String jobGroup,
										 String cronExpression) throws ScheduleException {

		//同步或异步

		try {


			TriggerKey triggerKey = getTriggerKey(jobName, jobGroup);

			//表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

			//按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
			// 忽略状态为PAUSED的任务，解决集群环境中在其他机器设置定时任务为PAUSED状态后，集群环境启动另一台主机时定时任务全被唤醒的bug
			if(!triggerState.name().equalsIgnoreCase("PAUSED")){
				//按新的trigger重新设置job执行
				scheduler.rescheduleJob(triggerKey, trigger);
			}
		} catch (SchedulerException e) {
			logger.error("updateScheduleJob error", e);
			throw new ScheduleException("updateScheduleJob error");
		}
	}

	/**
	 * 删除定时任务
	 *
	 * @param scheduler
	 * @param jobName
	 * @param jobGroup
	 */
	public static void deleteScheduleJob(Scheduler scheduler,
										 String jobName,
										 String jobGroup) throws ScheduleException {
		try {
			scheduler.deleteJob(getJobKey(jobName, jobGroup));
		} catch (SchedulerException e) {
			logger.error("deleteScheduleJob error", e);
			throw new ScheduleException("deleteScheduleJob");
		}
	}
}