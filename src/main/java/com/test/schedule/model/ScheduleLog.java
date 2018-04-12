package com.test.schedule.model;

import java.io.Serializable;
import java.util.Date;

public class ScheduleLog implements Serializable {

	private Integer logId;// 任务id

	private String executeJobName;// 执行的任务名称(同一任务组内任务名称必须唯一)

	private String executeJobGroup;// 任务组

	private String executeResult;// 执行结果

	private Date executeStartTime;// 执行开始时间

	private Date executeEndTime;// 执行结束时间
	
	private Integer isDelete;
	
	private Date createTime;// 创建时间

	private String creator;// 创建人(userId)

	private Date lastUpdateTime;// 最近更新时间

	private String lastUpdator;// 最近更新人(userId)

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getExecuteJobName() {
		return executeJobName;
	}

	public void setExecuteJobName(String executeJobName) {
		this.executeJobName = executeJobName;
	}

	public String getExecuteJobGroup() {
		return executeJobGroup;
	}

	public void setExecuteJobGroup(String executeJobGroup) {
		this.executeJobGroup = executeJobGroup;
	}

	public String getExecuteResult() {
		return executeResult;
	}

	public void setExecuteResult(String executeResult) {
		this.executeResult = executeResult;
	}

	public Date getExecuteStartTime() {
		return executeStartTime;
	}

	public void setExecuteStartTime(Date executeStartTime) {
		this.executeStartTime = executeStartTime;
	}

	public Date getExecuteEndTime() {
		return executeEndTime;
	}

	public void setExecuteEndTime(Date executeEndTime) {
		this.executeEndTime = executeEndTime;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getLastUpdator() {
		return lastUpdator;
	}

	public void setLastUpdator(String lastUpdator) {
		this.lastUpdator = lastUpdator;
	}

}