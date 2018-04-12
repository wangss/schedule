package com.test.schedule.model;

import java.io.Serializable;
import java.util.Date;

public class ScheduleJob implements Serializable {

	private Integer jobId;//任务id

	private String jobName;//任务名称(同一任务组内任务名称必须唯一)

	private String jobGroup;//任务组

	private Integer jobStatus;//任务运行状态 1为运行 0为停止

	private String cronExpression;//例如："0 0 1 * * *"

	private String description;//描述说明

	private String beanId;//Spring 定义任务的spring bean id

	private String beanName;//Spring定义任务的spring bean name

	private String methodName;//任务执行方法名

	private Integer isConcurrent;//是否并发执行

	private Date createTime;//创建时间

	private Integer creatorId;//创建人(userId)

	private Date lastUpdateTime;//最近更新时间

	private Integer updatorId;//最近更新人(userId)

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public Integer getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(Integer jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBeanId() {
		return beanId;
	}

	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Integer getIsConcurrent() {
		return isConcurrent;
	}

	public void setIsConcurrent(Integer isConcurrent) {
		this.isConcurrent = isConcurrent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getUpdatorId() {
		return updatorId;
	}

	public void setUpdatorId(Integer updatorId) {
		this.updatorId = updatorId;
	}

}