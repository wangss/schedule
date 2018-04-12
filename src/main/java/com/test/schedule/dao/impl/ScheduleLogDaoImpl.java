package com.test.schedule.dao.impl;


import com.junyi.cms.schedule.dao.ScheduleLogDao;
import com.junyi.cms.schedule.model.ScheduleLog;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * 类名称：ScheduleLogDaoImpl   
 * 类描述：   
 * 创建人：lifaqiu
 * 创建时间：2018年3月7日 上午11:55:28
 *
 */
@Repository
public class ScheduleLogDaoImpl implements ScheduleLogDao {

	private static String scheduleLogNameSpace = "com.junyi.cms.schedule.model.ScheduleLogMapper.";


	@Autowired
    SqlSession sqlSession;

	@Override
	public int insertScheduleLog(ScheduleLog scheduleLog) {

		return sqlSession.insert(scheduleLogNameSpace + "insertScheduleLog", scheduleLog);
	}
	
	@Override
	public List<ScheduleLog> getScheduleLog(Map<String, Object> params) {
		return sqlSession.selectList(scheduleLogNameSpace + "getScheduleLog", params);
	}

	@Override
	public int getScheduleLogCnt(Map<String, Object> params) {
		List<Integer> list = sqlSession.selectList(scheduleLogNameSpace + "getScheduleLogCnt", params);
		return list.get(0);
	}
}
