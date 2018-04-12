package com.test.schedule.controller;

import com.junyi.cms.common.QueryResult;
import com.junyi.cms.schedule.model.ScheduleLog;
import com.junyi.cms.schedule.service.ScheduleLogService;
import com.junyi.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("schedule/log")
public class ScheduleLogController {

	// 日志记录器
    private static final Logger logger = LoggerFactory.getLogger(ScheduleLogController.class);

	@Autowired
	private ScheduleLogService scheduleLogService;

    /**
     * 列表
     * @return
     */
	@RequestMapping(value = "/list")
	public ModelAndView logList(String executeJobName, String executeJobGroup, Integer page_size, Integer curr_page, String startTime, String endTime) {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("schedule/scheduleLog");

		int pageNo = curr_page == null?1:curr_page;
		int limitNum = page_size == null?10:page_size;
		int startNum = (pageNo - 1) * limitNum;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startNum", startNum);
		param.put("limitNum", limitNum);
		if (!StringUtil.isBlank(executeJobName)) {
			param.put("executeJobName", executeJobName.trim());
		}
		if (!StringUtil.isBlank(executeJobGroup)) {
			param.put("executeJobGroup", executeJobGroup.trim());
		}
		if (!StringUtil.isBlank(startTime)) {
			param.put("startTime", startTime.trim());
		}
		if (!StringUtil.isBlank(endTime)) {
			param.put("endTime", endTime.trim());
		}
		int total = scheduleLogService.getScheduleLogCnt(param);
		List<ScheduleLog> scheduleLogList = scheduleLogService.getScheduleLog(param);

		QueryResult<ScheduleLog> queryResult = new QueryResult<ScheduleLog>(new HashMap<>());

		queryResult.getParam().put("curr_page", pageNo);
		queryResult.getParam().put("page_size", limitNum);
		queryResult.getParam().put("executeJobName", executeJobName);
		queryResult.getParam().put("executeJobGroup", executeJobGroup);
		queryResult.getParam().put("startTime", startTime);
		queryResult.getParam().put("endTime", endTime);
		queryResult.calculate(total);
		queryResult.setList(scheduleLogList);

		mav.addObject("queryResult", queryResult);

		return mav;
	}
}
