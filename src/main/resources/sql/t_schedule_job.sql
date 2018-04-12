

DROP TABLE IF EXISTS `t_schedule_job`;
CREATE TABLE `t_schedule_job` (
  `job_id`  int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `job_name` varchar(100) NOT NULL DEFAULT '' COMMENT '任务名称(同一任务组内任务名称必须唯一)',
  `job_group` varchar(100) NOT NULL DEFAULT '' COMMENT '任务组',
  `job_status` TINYINT NOT NULL DEFAULT '0' COMMENT '任务运行状态',

  `cron_expression` varchar(255) NOT NULL DEFAULT '' COMMENT '例如："0 0 1 * * *"',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述说明',
  `bean_id` varchar(255) NOT NULL DEFAULT '' COMMENT 'Spring 定义任务的spring bean id',
  `bean_name` varchar(255) NOT NULL DEFAULT '' COMMENT 'Spring定义任务的spring bean name',
  `method_name` varchar(255) NOT NULL DEFAULT '' COMMENT '任务执行方法名',
  `is_concurrent` TINYINT NOT NULL DEFAULT '0' COMMENT '是否并发执行',

  `create_time` datetime NOT NULL DEFAULT now() comment '创建时间',
  `creator_id` int(11) UNSIGNED NOT NULL NOT NULL DEFAULT '0' COMMENT '创建人(userId)',
  `last_update_time` datetime NOT NULL DEFAULT now() COMMENT '最近更新时间',
  `updator_id` int(11) UNSIGNED NOT NULL DEFAULT '0' COMMENT '最近更新人(userId)',

  PRIMARY KEY (`job_id`),
  UNIQUE KEY `name_group` (`job_name`,`job_group`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_schedule_job
-- ----------------------------


INSERT INTO t_schedule_job
  (job_name, job_group , job_status, cron_expression, description, bean_id, bean_name, method_name, is_concurrent, create_time, creator_id, last_update_time, updator_id)
values
  ('订单超时未支付取消', '订单', '0', '0 0/1 * * * ?', '订单超过20分钟未支付取消', 'orderJob', 'com.junyi.cms.schedule.task.OrderJob', 'timedCancelUnpaidOrder',  '0', now(), 0, now(), 0),
  ('定时抓取订单物流信息', '订单', '0', '0 0 0/4 * * ?', '定时抓取订单物流信息', 'orderJob', 'com.junyi.cms.schedule.task.OrderJob', 'timedGetOrderDeliverInfo',  '0', now(), 0, now(), 0),
  ('订单反悔期任务', '订单', '0', '0 0/10 * * * ?', '订单反悔期任务', 'orderJob', 'com.junyi.cms.schedule.task.OrderJob', 'timedDoBackOnMission',  '0', now(), 0, now(), 0),
  ('渠道商保证金任务', '订单', '0', '0 0/10 * * * ?', '渠道商保证金任务', 'orderJob', 'com.junyi.cms.schedule.task.OrderJob', 'timedUpdateChannelDeposit',  '0', now(), 0, now(), 0),

  ('定时取消预占库存任务', '商品', '0', '0 0/10 * * * ?', '定时取消预占库存任务', 'goodsJob', 'com.junyi.cms.schedule.task.GoodsJob', 'releaseOrderCount',  '0', now(), 0, now(), 0)

;