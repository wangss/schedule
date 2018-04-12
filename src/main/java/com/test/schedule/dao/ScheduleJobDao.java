package com.test.schedule.dao;

import com.junyi.cms.schedule.model.ScheduleJob;
import com.test.schedule.model.ScheduleJob;

import java.util.List;
import java.util.Map;

/**
 * 定时任务mapper
 * @author shuisheng 2018-02-11
 */
public interface ScheduleJobDao {

	int insertScheduleJob(ScheduleJob obj);

	List<ScheduleJob> getScheduleJob(Map<String, Object> params);

	ScheduleJob getScheduleJobByJobId(long jobId);

	int updateScheduleJobByJobId(Map<String, Object> params);

	int getScheduleJobCnt(Map<String, Object> param);

	List<ScheduleJob> getScheduleJobByMap(Map<String, Object> param);

}