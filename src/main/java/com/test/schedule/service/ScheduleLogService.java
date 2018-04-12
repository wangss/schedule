package com.test.schedule.service;

import com.junyi.cms.schedule.model.ScheduleLog;

import java.util.List;
import java.util.Map;

public interface ScheduleLogService {

	int insertScheduleLog(ScheduleLog scheduleLog);

	int getScheduleLogCnt(Map<String, Object> params);
	
	List<ScheduleLog> getScheduleLog(Map<String, Object> params);

}
