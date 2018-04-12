package com.test.schedule.quartz;

import com.junyi.cms.schedule.log.ScheduleLogOper;
import com.junyi.cms.schedule.model.ScheduleJob;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

public class TaskUtils {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TaskUtils.class);

    /**
     * 通过反射调用scheduleJob中定义的方法
     *
     * @param
     */
    public static void invokeMethod(ScheduleJob scheduleJob) {
        Object object = null;
        Class clazz = null;

        if (StringUtils.isNotBlank(scheduleJob.getBeanId())) {
            object = SpringUtils.getBean(scheduleJob.getBeanId());

        } else if (StringUtils.isNotBlank(scheduleJob.getBeanName())) {
            try {
                clazz = Class.forName(scheduleJob.getBeanName());
                object = clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (object == null) {
            logger.error("schedule job {} start fail, please check", scheduleJob.getJobName());
            return;
        }
        clazz = object.getClass();
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
        } catch (NoSuchMethodException e) {
            logger.error("schedule job method fail {}, please check", scheduleJob.getJobName());

        } catch (SecurityException e) {
            e.printStackTrace();
        }

        if (method != null) {
            Object result = null;
            Date executeStartTime = new Date();
        	try {
        		result = method.invoke(object);
        		if(result == null){
        			result = "执行完成";
        		}else {
        			result = "执行完成，执行结果=" + result.toString();
				}
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                result = "执行出错，原因=" + e.getMessage();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                result = "执行出错，原因=" + e.getMessage();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                result = "执行出错，原因=" + e.getMessage();
            }
        	Date executeEndTime = new Date();
            // 执行定时任务日志
        	record(scheduleJob.getBeanName(),scheduleJob.getMethodName(),executeStartTime,executeEndTime, result.toString());
        }
    }
    
    /**
     * 执行定时任务日志
     * @param beanName
     * @param methodName
     * @param executeStartTime
     * @param executeEndTime
     * @param result
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static void record(String beanName,String methodName, Date executeStartTime, Date executeEndTime, String executeResult) {
    	ScheduleLogOper object = (ScheduleLogOper) SpringUtils.getBean("scheduleLogOper");
        object.recordLog(beanName, methodName, executeStartTime, executeEndTime, executeResult);
	}
}