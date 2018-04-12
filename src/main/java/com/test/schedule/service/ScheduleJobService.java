package com.test.schedule.service;

import com.junyi.cms.common.exception.ScheduleException;
import com.junyi.cms.schedule.model.ScheduleJob;

import java.util.List;
import java.util.Map;

public interface ScheduleJobService {
	
    /**
     * 初始化定时任务
     */
    public void initScheduleJob();


    /**
     * 查
     */
    public List<ScheduleJob> getScheduleJob() ;
    
    /**
     * 查
     */
    public List<ScheduleJob> getScheduleJobByMap(Map<String, Object> param) ;

    /**
     * 添加
     */
    public int addScheduleJob(ScheduleJob job);

    /**
     * 修改
     * @param params
     * @return
     */
    int updateScheduleJobByJobId(Map<String, Object> params);

    /**
     * 根据条件获取job
     * @param params
     * @return
     */
    ScheduleJob getScheduleJob(String beanName, String MethodName);


    /**
     * 停止任务
     *
     * @param jobId
     * @return
     */
    public void stopJob(long jobId) throws ScheduleException;


    /**
     * 运行任务
     *
     * @param jobId
     * @return
     */
    public void runJob(long jobId) throws ScheduleException;


    /**
     * 运行一次任务
     *
     * @param jobId
     * @return
     */
    public void runOnce(long jobId) throws ScheduleException;


	public int getScheduleJobCnt(Map<String, Object> param);

}
