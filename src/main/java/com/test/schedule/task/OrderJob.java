package com.test.schedule.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.junyi.cms.schedule.service.ScheduleLogService;
import com.junyi.framework.result.Result;
import com.junyi.order.service.OrderDeliverServiceApi;
import com.junyi.order.service.OrderServiceApi;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class OrderJob {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OrderJob.class);

    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Reference(version = "1.0.0", timeout=10000, retries=-1)
    private OrderServiceApi orderService;

    @Reference(version = "1.0.0", timeout=10000, retries=-1)
    private OrderDeliverServiceApi orderDeliverService;

	@Autowired
	private ScheduleLogService scheduleLogService;

    /**
     * 定时取消超时未确认订单， 一般1分钟一次
     * @author shuisheng 2018-02-27
     */
    public boolean timedCancelUnpaidOrder() {
        logger.info("timedCancelUnpaidOrder Begin...");
        boolean result = orderService.timedCancelUnpaidOrder(0, "system", 0, 20);//20表示超过20分钟未支付则取消
        logger.info("timedCancelUnpaidOrder End... result = {}", result);
        return result;
    }


    /**
     * 定时抓取订单物流信息, 一般4小时一次
     * @author shuisheng 2018-02-27
     */
    public boolean timedGetOrderDeliverInfo() {
        logger.info("timedGetOrderDeliverInfo Begin...");
        boolean result = orderService.timedCancelUnpaidOrder(0, "system", 0, 20);//20表示超过20分钟未支付则取消
        logger.info("timedGetOrderDeliverInfo End... result = {}", result);
        return result;
    }

    /**
     * 定时执行反悔期任务, 一般10分钟一次
     * @author Joseph 2018-02-27
     */
    public Result timedDoBackOnMission() {
        logger.info("timedDoBackOnMission Begin...");
        Result result = orderService.goBackOnMission();
        logger.info("timedDoBackOnMission End... result = {}", result.isSuccess());
        return result;
    }

    /**
     * 执行渠道商保证金任务, 一般10分钟一次
     * @author Joseph 2018-02-27
     */
    public Result timedUpdateChannelDeposit() {
//        logger.info("timedUpdateChannelDeposit Begin...");
//        Result result = orderService.updateChannelDeposit();
//        logger.info("timedUpdateChannelDeposit End... result = {}", result.isSuccess());
//        return result;
        return new Result();
    }


    /**
     * 测试定时取消订单
     */
    public void cancelOrder() {
        System.out.println(new Date() + "...cancelOrder running...");
    }

    /**
     * 测试定时更新物流信息
     */
    public void orderDeliver() {
        System.out.println(new Date() + "...orderDeliver running...");
    }
}
