<!DOCTYPE html>
<html lang="en">
<head>
    <title>定时任务管理</title>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script type="text/javascript" src="/js/jquery-1.11.3.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
</head>

<body>
<!-- 模态框（Modal） -->
<div class="modal fade" id="eidtModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
				</button>
				<h4 class="modal-title" id="myModalLabel">
					
				</h4>
			</div>
			<div class="modal-body">
				<form id="fm" role="form">
					<input type="hidden" id="jobId" name="jobId">
				  	<div class="form-group">
				    	<label for="jobName">任务名称：</label>
				    	<input type="text" id="jobName" name="jobName" class="form-control" placeholder="输入任务名称">
				  	</div>
				  	<div class="form-group">
				    	<label for="jobGroup">任务组：</label>
				    	<input type="text" id="jobGroup" name="jobGroup" class="form-control" placeholder="输入任务组">
				  	</div>
				  	<div class="form-group">
				    	<label for="cronExpression">任务周期：</label>
				    	<input type="text" id="cronExpression" name="cronExpression" class="form-control" placeholder="输入任务周期，例如：0 0 1 * * *">
				  	</div>
				  	<div class="form-group">
				    	<label for="jobName">描述说明：</label>
				    	<textarea id="description" name="description" class="form-control" rows="3"></textarea>	
				    </div>
				  	<div class="form-group">
				    	<label for="beanId">beanId：</label>
				    	<input type="text" id="beanId" name="beanId" class="form-control" placeholder="输入beanId">
				  	</div>
				  	<div class="form-group">
				    	<label for="beanName">运行类：</label>
				    	<input type="text" id="beanName" name="beanName" class="form-control" placeholder="输入运行类">
				  	</div>
				  	<div class="form-group">
				    	<label for="methodName">运行方法：</label>
				    	<input type="text" id="methodName" name="methodName" class="form-control" placeholder="输入运行方法">
				  	</div>
				  	<div class="form-group">
				    	<label for="isConcurrent">是否同步：</label>
				    	<input type="radio" name="isConcurrent" id="isConcurrent1" value="0">否
				    	<input type="radio" name="isConcurrent" id="isConcurrent2" value="1">是
				    </div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					关闭
				</button>
				<button type="button" class="btn btn-primary" onclick="addOrUpdateJob()">
					提交更改
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<div class="wrapper">
    <#include "../head.ftl"/>
    <div class="main-content">
        
        <div class="panel panel-default"  style="margin-bottom: 10px;margin-right: 100px;">
            <div class="panel-heading">
                <h3 class="panel-title">定时任务管理列表</h3>
            </div>
            <div class="panel-body" style="padding: 10px;">
                <form class="form-inline" style="margin-bottom: 0px;"  method="post" action="list">
                    <div class="form-group">
                        <input type="text" class="form-control" id="jobName"  name="jobName" placeholder="jobName" value="${queryResult.param['jobName']!''}">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" id="jobGroup"  name="jobGroup" placeholder="jobGroup" value="${queryResult.param['jobGroup']!''}">
                    </div>

                    <button type="submit" class="btn btn-default">查找</button>

                    <button type="button" onclick="_add()" class="btn btn-info">增加</button>

				</form>
            </div>
        </div>

		<#if queryResult??>


            <div class="panel panel-default"  style="margin-bottom: 10px;margin-right: 100px;">
                <!-- Table -->
                <table class="table  table-bordered table-hover table-striped">
                    <thead>
                    <tr>
                        <th>id</th>

                        <th>name</th>
                        <th>group</th>

                        <th>状态</th>
                        <th>周期</th>
                        <th>描述</th>
                        <th>BeanId</th>
                        <th>运行类</th>
                        <th>运行方法</th>
                        <th>是否同步</th>
                        <th>操作</th>
                    </tr>


                    </thead>
                    <tbody>

                    <#list queryResult.list as job>
                        <tr>
                            <td>${job.jobId}</td>

                            <td>${job.jobName}</td>
                            <td>${job.jobGroup}</td>
                            <td>
                            	<#if job.jobStatus == 1><font style="color:green;">运行中</font></#if>
                            	<#if job.jobStatus == 0><font style="color:red;">已停止</font></#if>
							</td>
                            <td>${job.cronExpression}</td>

                            <td>${job.description}</td>

                            <td>${job.beanId}</td>
                            <td>${job.beanName}</td>

                            <td>${job.methodName}</td>
                            <td>
                            	<#if job.isConcurrent == 1>是</#if>
                            	<#if job.isConcurrent == 0>否</#if>
                            </td>
                            <td>
						        <#if job.jobStatus == 1>
									<button type="button" class="btn btn-danger" onclick="_stop(${job.jobId},'${job.jobName}')">停止</button>
                            		<button type="button" class="btn btn-default" onclick="_runOnce(${job.jobId},'${job.jobName}')">运行一次</button>
                            	</#if>

								<#if job.jobStatus == 0>
                            		<button type="button" class="btn btn-default" onclick="_edit(${job.jobId},'${job.jobName}','${job.jobGroup}','${job.cronExpression}','${job.description}','${job.beanId}','${job.beanName}','${job.methodName}',${job.isConcurrent})">编辑</button>
                            		<button type="button" class="btn btn-default" onclick="_run(${job.jobId},'${job.jobName}')">运行</button>
                            		<button type="button" class="btn btn-default" onclick="_runOnce(${job.jobId},'${job.jobName}')">运行一次</button>
                            	</#if>
                            </td>

                        </tr>

					</#list>

                    </tbody>
                </table>
            </div>


			<#assign url = "list">
			<#include "../RollPage.ftl"/>
            <#--<tf:RollPage url="list" />-->
		</#if>

    </div>


</div>

<script type="text/javascript">
	
   	$(function () { 
   		$('#eidtModal').modal('hide');
   	});

	function addOrUpdateJob(){
		$.ajax({  
			type: "POST",   //提交的方法
            url:"addOrUpdateJob", //提交的地址
            data:$('#fm').serialize(),// 序列化表单值  
            async: false,  
            error: function(request) {  //失败的话
                 alert("Connection error");  
            },  
            success: function(data) {  //成功
				data = JSON.parse(data);
                 if(data.code == 0){
                 	$('#eidtModal').modal('hide');
                 	location.reload();
                 }else{
                 	alert(data.msg);  
                 }
            }
		});
	}
	function _run(jobId, jobName){
		if (confirm("确定要运行'"+jobName+"'定时任务吗")){ 
			location.href="run?jobId="+jobId;
		}
	}
	
	function _runOnce(jobId, jobName){
		if (confirm("确定要运行一次'"+jobName+"'定时任务吗")){ 
			location.href="runonce?scheduleJobId="+jobId;
		}
	}
	
	function _edit(jobId, jobName, jobGroup, cronExpression, description, beanId, beanName, methodName, isConcurrent){
		$('#myModalLabel').text("修改定时任务信息");
		$('#jobId').val(jobId);
		$('#jobName').val(jobName);
		$('#jobGroup').val(jobGroup);
		$('#cronExpression').val(cronExpression);
		$('#description').val(description);
		$('#beanId').val(beanId);
		$('#beanName').val(beanName);
		$('#methodName').val(methodName);
		if(isConcurrent == 1){
			$('#isConcurrent1').prop('checked',false);
			$('#isConcurrent2').prop('checked',true);
		}else{
			$('#isConcurrent1').prop('checked',true);
			$('#isConcurrent2').prop('checked',false);
		}
		
		$('#eidtModal').modal('show');
	}
	
	function _add(){
		$('#myModalLabel').text("增加定时任务信息");
		$('#jobId').val("0");
		$('#jobName').val("");
		$('#jobGroup').val("");
		$('#cronExpression').val("");
		$('#description').val("");
		$('#beanId').val("");
		$('#beanName').val("");
		$('#methodName').val("");
		$('#isConcurrent1').prop('checked',true);
		$('#isConcurrent2').prop('checked',false);
		
		$('#eidtModal').modal('show');
	}
	
	function _stop(jobId, jobName){
		if (confirm("确定要停止'"+jobName+"'定时任务吗")){ 
			location.href="stop?scheduleJobId="+jobId;
		}
	}
</script>
</body>
</html>
