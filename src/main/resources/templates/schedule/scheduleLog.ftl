<!DOCTYPE html>
<html lang="en">
<head>
    <title>定时任务管理</title>
    <script type="text/javascript" src="/date/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="/date/jquery-ui-1.8.17.custom.min.js"></script>
	<script type="text/javascript" src="/date/jquery-ui-timepicker-addon.js"></script>
    <script type="text/javascript" src="/date/jquery-ui-timepicker-zh-CN.js"></script>
</head>

<body>

<div class="wrapper">
    <#include "../head.ftl"/>


<script type="text/javascript">
	$(function () {
        $(".ui_timepicker").datetimepicker({
            //showOn: "button",
            //buttonImage: "./css/images/icon_calendar.gif",
            //buttonImageOnly: true,
            showSecond: true,
            timeFormat: 'hh:mm:ss',
            stepHour: 1,
            stepMinute: 1,
            stepSecond: 1
        })
        
    })
	
</script>
    <div class="main-content">
        <div class="panel panel-default"  style="margin-bottom: 10px;margin-right: 100px;">
            <div class="panel-heading">
                <h3 class="panel-title">定时任务日志列表</h3>
            </div>
            <div class="panel-body" style="padding: 10px;">
                <form class="form-inline" style="margin-bottom: 0px;"  method="post" action="list">
                    <div class="form-group">
                        <input type="text" class="form-control" id="executeJobName"  name="executeJobName" placeholder="executeJobName" value="${queryResult.param['executeJobName']!''}">
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" id="executeJobGroup"  name="executeJobGroup" placeholder="executeJobGroup" value="${queryResult.param['executeJobGroup']!''}">
                    </div>
                    <div class="form-group">
                        执行开始时间区间：
                        <input type="text" name="startTime" class="ui_timepicker"
                         style="height: 34px;padding: 6px 12px;font-size: 14px;line-height: 1.42857143;color: #555;"
                          placeholder="点击选择时间" value="${queryResult.param['startTime']!''}" readonly="readonly">
                        -
                        <input type="text" name="endTime" class="ui_timepicker"
                         style="height: 34px;padding: 6px 12px;font-size: 14px;line-height: 1.42857143;color: #555;"
                          placeholder="点击选择时间" value="${queryResult.param['endTime']!''}" readonly="readonly">
                    </div>
					
                    <button type="submit" class="btn btn-default">查找</button>
                </form>
            </div>
        </div>

            <#if queryResult??>
            <div class="panel panel-default"  style="margin-bottom: 10px;margin-right: 100px;">
                <!-- Table -->
                <table class="table  table-bordered table-hover table-striped">
                    <thead>
	                    <tr>
	                        <th>日志id</th>
	                        <th>执行的任务名称</th>
	                        <th>所属任务组</th>
	                        <th>执行开始时间</th>
	                        <th>执行结果</th>
	                        <th>执行结束时间</th>
	                    </tr>
                    </thead>
                    <tbody>
                    <#list queryResult.list as log>
                        <tr>
                            <td>${log.logId}</td>
                            <td>${log.executeJobName}</td>
                            <td>${log.executeJobGroup}</td>
                            <td>${log.executeStartTime?string("yyyy-MM-dd HH:mm:ss zzzz")}</td>
                            <td>${log.executeResult}</td>
                            <td>${log.executeEndTime?string("yyyy-MM-dd HH:mm:ss zzzz")}</td>
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

</body>
</html>
