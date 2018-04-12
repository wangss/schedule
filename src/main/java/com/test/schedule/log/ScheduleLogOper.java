package com.test.schedule.log;

import com.junyi.cms.schedule.model.ScheduleJob;
import com.junyi.cms.schedule.model.ScheduleLog;
import com.junyi.cms.schedule.service.ScheduleJobService;
import com.junyi.cms.schedule.service.ScheduleLogService;
import com.test.schedule.service.ScheduleJobService;
import com.test.schedule.service.ScheduleLogService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务日志操作类
 * 类名称：ScheduleLogOper   
 * 类描述：   
 * 创建人：lifaqiu
 * 创建时间：2018年3月7日 下午3:12:29
 *
 */
@Component
public class ScheduleLogOper {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ScheduleLogOper.class);

	@Autowired
	private ScheduleLogService scheduleLogService;
	@Autowired
	private ScheduleJobService scheduleJobService;

	/**
	 * 执行定时任务日志
	 * @param beanName
	 * @param methodName
	 * @param executeStartTime
	 * @param executeEndTime
	 * @param executeResult
	 */
	public void recordLog(String beanName, String methodName, Date executeStartTime, Date executeEndTime, String executeResult){
		logger.info("执行定时任务日志开始");
        ScheduleJob scheduleJob = scheduleJobService.getScheduleJob(beanName, methodName);
        if (scheduleJob == null) {
        	logger.error("执行定时任务日志结束，找不到scheduleJob，参数beanName="+beanName+"，methodName="+methodName);
        	return;
		}
        
        // 增加日志
        ScheduleLog scheduleLog = new ScheduleLog();
        scheduleLog.setCreateTime(new Date());
        scheduleLog.setCreator("system");
        scheduleLog.setExecuteEndTime(executeEndTime);
        scheduleLog.setExecuteJobGroup(scheduleJob.getJobGroup());
        scheduleLog.setExecuteJobName(scheduleJob.getJobName());
        scheduleLog.setExecuteResult(executeResult);
        scheduleLog.setExecuteStartTime(executeStartTime);
        scheduleLog.setIsDelete(0);
        scheduleLog.setLastUpdateTime(new Date());
        scheduleLog.setLastUpdator("system");
        try {
        	scheduleLogService.insertScheduleLog(scheduleLog);
        	logger.info("插入执行定时任务日志成功");
		} catch (Exception e) {
			logger.error("插入执行定时任务日志失败，原因：" + e.getMessage());
		}
	}

}
