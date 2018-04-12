package com.test.schedule.event;

import com.junyi.cms.schedule.service.ScheduleJobService;
import com.test.schedule.service.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 定时任务初始化
 * @author shuisheng 2018-02-11
 */
@Component
public class ScheduleJobInit {

    /** 定时任务service */
    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 项目启动时初始化
     */
    @PostConstruct
    public void init() {
        scheduleJobService.initScheduleJob();
    }

}
