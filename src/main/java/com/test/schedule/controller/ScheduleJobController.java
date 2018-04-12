package com.test.schedule.controller;

import com.junyi.cms.common.QueryResult;
import com.junyi.cms.common.exception.ScheduleException;
import com.junyi.cms.login.model.User;
import com.junyi.cms.schedule.model.ScheduleJob;
import com.junyi.cms.schedule.service.ScheduleJobService;
import com.junyi.framework.util.StringUtil;
import com.test.login.model.User;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("schedule/job")
public class ScheduleJobController {

	// 日志记录器
    private static final Logger logger = LoggerFactory.getLogger(ScheduleJobController.class);

	@Autowired
	private ScheduleJobService scheduleJobService;

    /**
     * 列表
     * @return
     */
	@RequestMapping(value = "/list")
	public ModelAndView jobList(String jobName, String jobGroup, Integer page_size, Integer curr_page) {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("schedule/scheduleJob");
		int pageNo = curr_page == null?1:curr_page;
		int limitNum = page_size == null?10:page_size;
		int startNum = (pageNo - 1) * limitNum;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startNum", startNum);
		param.put("limitNum", limitNum);
		if (!StringUtil.isBlank(jobName)) {
			param.put("jobName", jobName.trim());
		}
		if (!StringUtil.isBlank(jobGroup)) {
			param.put("jobGroup", jobGroup.trim());
		}
		int total = scheduleJobService.getScheduleJobCnt(param);
		List<ScheduleJob> scheduleJobList = scheduleJobService.getScheduleJobByMap(param);

		QueryResult<ScheduleJob> queryResult = new QueryResult<ScheduleJob>(new HashMap<>());

		queryResult.getParam().put("curr_page", pageNo);
		queryResult.getParam().put("page_size", limitNum);
		queryResult.getParam().put("jobName", jobName);
		queryResult.getParam().put("jobGroup", jobGroup);
		queryResult.calculate(total);
		queryResult.setList(scheduleJobList);

		mav.addObject("queryResult", queryResult);

		return mav;
	}

    /**
     * 运行
     *
     * @return
     */
    @RequestMapping(value = "/run")
    public ModelAndView resumeScheduleJob(long jobId) {
        try {
			scheduleJobService.runJob(jobId);
		} catch (ScheduleException e) {
			
			e.printStackTrace();
		}

        return jobList(null,null,10,1);

    }


    /**
     * 停止
     *
     * @return
     */
    @RequestMapping(value = "/stop")
    public ModelAndView pauseScheduleJob(Long scheduleJobId) {
        try {
			scheduleJobService.stopJob(scheduleJobId);
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return jobList(null,null,10,1);

    }


    /**
     * 运行一次
     *
     * @return
     */
    @RequestMapping(value = "/runonce")
    public ModelAndView runOnceScheduleJob(Long scheduleJobId) {

        try {
			scheduleJobService.runOnce(scheduleJobId);
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return jobList(null,null,10,1);
    }
    

    /**
     * 修改任务信息
     *
     * @return
     */
    @RequestMapping(value = "/addOrUpdateJob")
    @ResponseBody
    public Map<String, Object> addOrUpdateJob(int jobId, String jobName, String jobGroup, 
    		String cronExpression, String description, String beanId, 
    		String beanName, String methodName, int isConcurrent, HttpServletRequest request) {
    	Map<String, Object> returnMap = new HashMap<String, Object>();

    	if (jobName == null || "".equals(jobName.trim())) {
        	returnMap.put("code", -1);
        	returnMap.put("msg", "任务名称不能为空");
            return returnMap;
		}
    	if (jobGroup == null || "".equals(jobGroup.trim())) {
        	returnMap.put("code", -1);
        	returnMap.put("msg", "任务组不能为空");
            return returnMap;
		}
    	if (!validCronExpression(cronExpression)) {
        	returnMap.put("code", -1);
        	returnMap.put("msg", "任务周期格式不正确");
            return returnMap;
		}
    	description = description == null?"":description.trim();
    	if (beanId == null || "".equals(beanId.trim())) {
        	returnMap.put("code", -1);
        	returnMap.put("msg", "任务组不能为空");
            return returnMap;
		}
    	if (beanId == null || "".equals(beanId.trim())) {
        	returnMap.put("code", -1);
        	returnMap.put("msg", "Spring定义任务的spring bean id不能为空");
            return returnMap;
		}
    	if (beanName == null || "".equals(beanName.trim())) {
        	returnMap.put("code", -1);
        	returnMap.put("msg", "Spring定义任务的spring bean name不能为空");
            return returnMap;
		}
    	if (methodName == null || "".equals(methodName.trim())) {
        	returnMap.put("code", -1);
        	returnMap.put("msg", "任务执行方法名不能为空");
            return returnMap;
		}
    	
    	User user = (User)request.getSession().getAttribute("userOnline");

		if(user == null){
			returnMap.put("code", -1);
        	returnMap.put("msg", "请重新登录");
            return returnMap;
		}
		
    	if (jobId == 0) {
			// 增加
    		ScheduleJob job = new ScheduleJob();
    		job.setBeanId(beanId.trim());
    		job.setBeanName(beanName.trim());
    		job.setCreateTime(new Date());
    		job.setCreatorId(Integer.parseInt(String.valueOf(user.getId())));
    		job.setCronExpression(cronExpression.trim());
    		job.setDescription(description.trim());
    		job.setIsConcurrent(isConcurrent);
    		job.setJobGroup(jobGroup.trim());
    		job.setJobName(jobName.trim());
    		job.setJobStatus(0);
    		job.setLastUpdateTime(new Date());
    		job.setMethodName(methodName.trim());
    		job.setUpdatorId(Integer.parseInt(String.valueOf(user.getId())));
    		try {
    			if (scheduleJobService.addScheduleJob(job) > 0) {
            		returnMap.put("code", 0);
                	returnMap.put("msg", "增加成功");
    			}else {
    				returnMap.put("code", -1);
    		    	returnMap.put("msg", "增加失败");
    			}
			} catch (Exception e) {
				e.printStackTrace();
				returnMap.put("code", -1);
		    	returnMap.put("msg", "增加失败");
			}
		}else {
			// 修改
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("jobId", jobId);
	    	params.put("jobName", jobName);
	    	params.put("jobGroup", jobGroup);
	    	params.put("cronExpression", cronExpression.trim());
	    	params.put("description", description.trim());
	    	params.put("beanId", beanId.trim());
	    	params.put("beanName", beanName.trim());
	    	params.put("methodName", methodName.trim());
	    	params.put("isConcurrent", isConcurrent);
	    	params.put("lastUpdateTime", new Date());
	    	params.put("updatorId", Integer.parseInt(String.valueOf(user.getId())));
        	try {
        		if (scheduleJobService.updateScheduleJobByJobId(params) > 0) {
            		returnMap.put("code", 0);
                	returnMap.put("msg", "修改成功");
    			}else {
    				returnMap.put("code", -1);
    		    	returnMap.put("msg", "修改失败");
    			}
			} catch (Exception e) {
				e.printStackTrace();
				returnMap.put("code", -1);
		    	returnMap.put("msg", "修改失败");
			}
		}
    	
        return returnMap;
    }

    /**
     * 验证cron_expression表达式
     * @param cron_expression
     * @return
     */
    private boolean validCronExpression(String cron_expression){
    	if (cron_expression == null) {
			return false;
		}
    	try {  
	        CronExpression exp = new CronExpression(cron_expression.trim());  
	        SimpleDateFormat df = new SimpleDateFormat("YYYYMMDD HH:mm:ss");  
	        Date d = new Date();  
	        int i = 0;  
	        // 循环得到接下来n此的触发时间点，供验证  
	        while (i < 10) {  
	            d = exp.getNextValidTimeAfter(d);  
	            df.format(d);  
	            ++i;  
	        }  
	        return true;
	    } catch (ParseException e) {  
	        e.printStackTrace();  
	    }
    	return false;
    }
}
