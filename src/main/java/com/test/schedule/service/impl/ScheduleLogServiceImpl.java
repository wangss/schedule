package com.test.schedule.service.impl;

import com.junyi.cms.schedule.dao.ScheduleLogDao;
import com.junyi.cms.schedule.model.ScheduleLog;
import com.junyi.cms.schedule.service.ScheduleLogService;
import com.test.schedule.service.ScheduleLogService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 
 * 类名称：ScheduleLogServiceImpl   
 * 类描述：   
 * 创建人：lifaqiu
 * 创建时间：2018年3月7日 下午1:44:09
 *
 */
@Service
public class ScheduleLogServiceImpl  implements ScheduleLogService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ScheduleLogServiceImpl.class);

    @Autowired
    private ScheduleLogDao scheduleLogDao;

    @Override
    public int insertScheduleLog(ScheduleLog scheduleLog) {
    	
    	return scheduleLogDao.insertScheduleLog(scheduleLog);
    }
    
    @Override
    public List<ScheduleLog> getScheduleLog(Map<String, Object> params) {
    	
    	return scheduleLogDao.getScheduleLog(params);
    }

	@Override
	public int getScheduleLogCnt(Map<String, Object> params) {
		return scheduleLogDao.getScheduleLogCnt(params);
	}    
}