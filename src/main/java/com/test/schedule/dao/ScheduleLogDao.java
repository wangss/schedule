package com.test.schedule.dao;

import com.junyi.cms.schedule.model.ScheduleLog;
import com.test.schedule.model.ScheduleLog;

import java.util.List;
import java.util.Map;

/**
 * 执行定时任务的日志
 * 类名称：ScheduleLogDao   
 * 类描述：   
 * 创建人：lifaqiu
 * 创建时间：2018年3月7日 上午11:51:37
 *
 */
public interface ScheduleLogDao {

	int insertScheduleLog(ScheduleLog scheduleLog);

	List<ScheduleLog> getScheduleLog(Map<String, Object> params);

	int getScheduleLogCnt(Map<String, Object> params);

}