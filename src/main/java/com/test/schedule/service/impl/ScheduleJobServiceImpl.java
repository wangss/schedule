package com.test.schedule.service.impl;

import com.alibaba.fastjson.JSON;
import com.junyi.cms.common.exception.ScheduleException;
import com.junyi.cms.schedule.dao.ScheduleJobDao;
import com.junyi.cms.schedule.log.ScheduleLogOper;
import com.junyi.cms.schedule.manager.ScheduleJobManager;
import com.junyi.cms.schedule.model.ScheduleJob;
import com.junyi.cms.schedule.quartz.TaskUtils;
import com.junyi.cms.schedule.service.ScheduleJobService;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shuisheng 2018-02-11
 */
@Service
public class ScheduleJobServiceImpl  implements ScheduleJobService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleJobServiceImpl.class);

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private ScheduleJobDao scheduleJobDao;
    
    @Autowired
    private ScheduleLogOper scheduleLogOper;

    /**
     * 初始化各个任务
     */
    public void initScheduleJob() {
        logger.info("initScheduleJob start...");
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        List<ScheduleJob> scheduleJobList = getScheduleJob();

        if (CollectionUtils.isEmpty(scheduleJobList)) {
            return;
        }

        for (ScheduleJob scheduleJob : scheduleJobList) {
            logger.debug("initScheduleJob createScheduleJob:{}", scheduleJob == null ? null : JSON.toJSONString(scheduleJob));
            if (scheduleJob.getJobStatus() != null && scheduleJob.getJobStatus() == 1) {
            	try {
                    CronTrigger cronTrigger = ScheduleJobManager.getCronTrigger(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
                    //不存在，创建一个
                    if (cronTrigger == null) {
                        ScheduleJobManager.createScheduleJob(scheduler, scheduleJob);
                    } else {
                        //已存在，那么更新相应的定时设置
                        ScheduleJobManager.updateScheduleJob(scheduler, scheduleJob);
                    }
                } catch (ScheduleException e) {
                    logger.error("initScheduleJob jobName {} jobGroup {} fail", scheduleJob.getJobName(), scheduleJob.getJobGroup());
                }
			}
        }
    }

    @Override
    public List<ScheduleJob> getScheduleJobByMap(Map<String, Object> param) {
        return scheduleJobDao.getScheduleJobByMap(param);
    }

	@Override
	public List<ScheduleJob> getScheduleJob() {
		return scheduleJobDao.getScheduleJob(new HashMap<String, Object>());
	}
    
    @Override
    public int getScheduleJobCnt(Map<String, Object> param) {
    	
    	return scheduleJobDao.getScheduleJobCnt(param);
    }

    /**
     * 暂时先直接数据库插入吧
     * @param job
     */
    @Override
    public int addScheduleJob(ScheduleJob job) {
        return scheduleJobDao.insertScheduleJob(job);
    }
    
    @Override
    public int updateScheduleJobByJobId(Map<String, Object> params) {
    	
    	return scheduleJobDao.updateScheduleJobByJobId(params);
    }
    
    @Override
    public ScheduleJob getScheduleJob(String beanName, String MethodName) {
    	HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("pBeanName", beanName);
        params.put("pMethodName", MethodName);
        List<ScheduleJob> list = scheduleJobDao.getScheduleJob(params);
    	return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    @Override
    public void stopJob(long jobId) throws ScheduleException {
        ScheduleJob scheduleJob = scheduleJobDao.getScheduleJobByJobId(jobId);

        if (scheduleJob != null && scheduleJob.getJobStatus() != null && scheduleJob.getJobStatus() == 1) {
            try {
            	ScheduleJobManager.pauseJob(schedulerFactoryBean.getScheduler(),
                        scheduleJob.getJobName(),
                        scheduleJob.getJobGroup());
                // 修改数据的状态job_status=0任务运行状态 1为运行 0为停止
            	Map<String, Object> params = new HashMap<String, Object>();
            	params.put("jobId", scheduleJob.getJobId());
            	params.put("jobStatus", 0);
            	params.put("lastUpdateTime", new Date());
            	int i = scheduleJobDao.updateScheduleJobByJobId(params);
            	if (i > 0) {
            		scheduleLogOper.recordLog(scheduleJob.getBeanName(), 
            				scheduleJob.getMethodName(), new Date(), new Date(), "停止");
				}
			} catch (Exception e) {
				logger.error("stopJob jobName {} jobGroup {} fail", scheduleJob.getJobName(), scheduleJob.getJobGroup());
			}
        }
    }

    @Override
    public void runJob(long jobId) throws ScheduleException {
        ScheduleJob scheduleJob = scheduleJobDao.getScheduleJobByJobId(jobId);

        if (scheduleJob != null && scheduleJob.getJobStatus() != null && scheduleJob.getJobStatus() == 0) {
        	try {
        		Scheduler scheduler = schedulerFactoryBean.getScheduler();
        		CronTrigger cronTrigger = ScheduleJobManager.getCronTrigger(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
                //不存在，创建一个
                if (cronTrigger == null) {
                    ScheduleJobManager.createScheduleJob(scheduler, scheduleJob);
                }
                ScheduleJobManager.resumeJob(schedulerFactoryBean.getScheduler(),
                        scheduleJob.getJobName(),
                        scheduleJob.getJobGroup());
                // 修改数据的状态job_status=1任务运行状态 1为运行 0为停止
            	Map<String, Object> params = new HashMap<String, Object>();
            	params.put("jobId", scheduleJob.getJobId());
            	params.put("jobStatus", 1);
            	params.put("lastUpdateTime", new Date());
            	int i = scheduleJobDao.updateScheduleJobByJobId(params);
            	if (i > 0) {
            		scheduleLogOper.recordLog(scheduleJob.getBeanName(), 
            				scheduleJob.getMethodName(), new Date(), new Date(), "唤醒");
				}
			} catch (Exception e) {
				logger.error("runJob jobName {} jobGroup {} fail", scheduleJob.getJobName(), scheduleJob.getJobGroup());
				throw e;
			}
        }
    }



    @Override
    public void runOnce(long jobId) throws ScheduleException {
        ScheduleJob scheduleJob = scheduleJobDao.getScheduleJobByJobId(jobId);
        if (scheduleJob != null) {
        	try {
        		Date executeStartTime = new Date();
        		TaskUtils.invokeMethod(scheduleJob);
        		Date executeEndTime = new Date();
        		scheduleLogOper.recordLog(scheduleJob.getBeanName(), 
        				scheduleJob.getMethodName(), executeStartTime, executeEndTime, "运行一次");
			} catch (Exception e) {
				logger.error("runOnce jobName {} jobGroup {} fail", scheduleJob.getJobName(), scheduleJob.getJobGroup());
				throw e;
			}
        }
    }
}