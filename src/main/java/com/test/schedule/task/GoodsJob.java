package com.test.schedule.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.junyi.goods.service.ApiForFixedTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Component
public class GoodsJob {

	private final static Logger log = LoggerFactory.getLogger(GoodsJob.class);

    @Reference(version = "1.0.0", timeout=10000, retries=-1)
    private ApiForFixedTaskService apiForFixedTaskService;
    
    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // 查询记录要用到
    String beanName = GoodsJob.class.getName();
    
    /**
     * 定时取消预占库存任务
     */
    public boolean releaseOrderCount() {
    	log.debug("定时取消预占库存任务开始");
        boolean flag = apiForFixedTaskService.releaseOrderCount();
        log.debug("定时取消预占库存任务执行结果=" + flag);
        log.debug("定时取消预占库存任务结束");
        return flag;
    }
    
}
