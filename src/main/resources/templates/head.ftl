
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>CMS-schedule-job</title>

    <!-- Bootstrap -->
    <link href="/css/bootstrap.min.css" rel="stylesheet"/>

    <link href="/css/base.css" rel="stylesheet"/>
    <link href="/css/skin.css" rel="stylesheet"/>
    
     <link type="text/css" href="/date/jquery-ui-1.8.17.custom.css" rel="stylesheet" />
     <link type="text/css" href="/date/jquery-ui-timepicker-addon.css" rel="stylesheet" />

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

    <div class="main-header">
        <a class="logo" href="#"><b>cms</b></a>

        <nav class="navbar navbar-static-top">
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">

                    <li style="background-color: #367FA9;">

                        <a style="color: #ffffff;">${Session.userOnline.nickname}</a>
                    </li>

                    <li style="background-color: #367FA9;">
                        <a style="color: #ffffff;" href="/login/out">退出</a>
                    </li>

                </ul>
            </div>
        </nav>
    </div>

    <div class="main-sidebar">
        <div class="sidebar" style="height:auto;">

            <ul class="sidebar-menu">

                <li>
                    <a href="/schedule/job/list"><span>定时任务管理</span></a>
                </li>
				<li>
                    <a href="/schedule/log/list"><span>定时任务日志</span></a>
                </li>

            </ul>
        </div>
    </div>

</body>
</html>
