<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/18
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no" />
    <meta name="format-detection" content="telephone=no" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Dashboard</title>
    <meta name="description" content="这是一个 login 页面">
    <meta name="keywords" content="login">
    <meta name="renderer" content="webkit">
    <meta name="apple-mobile-web-app-title" content="Dashboard" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="icon" type="image/png" href="<%=basePath%>/img/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="<%=basePath%>/img/app-icon72x72@2x.png">
    <link rel="stylesheet" href="<%=basePath%>/css/amazeui.min.css" />
    <link rel="stylesheet" href="<%=basePath%>/css/amazeui.datatables.min.css" />
    <link rel="stylesheet" href="<%=basePath%>/css/app.css">
    <script src="<%=basePath%>/js/jquery.min.js"></script>
</head>

<body data-type="login">
<div class="am-g tpl-g">
    <!-- 风格切换 -->
    <div class="tpl-skiner">
        <div class="tpl-skiner-toggle am-icon-cog">
        </div>
        <div class="tpl-skiner-content">
            <div class="tpl-skiner-content-title">
                选择主题
            </div>
            <div class="tpl-skiner-content-bar">
                <span class="skiner-color skiner-white" data-color="theme-white"></span>
                <span class="skiner-color skiner-black" data-color="theme-black"></span>
            </div>
        </div>
    </div>
    <div class="tpl-login">
        <div class="tpl-login-content">
            <div class="tpl-header-logo" style="margin: 10px auto;">
                <a href="javascript:;" style="font-size: 24px;">IOS事业部数据后台</a>
            </div>

            <form class="am-form tpl-form-line-form" name="form" action="<%=basePath%>/dashboard/login" method="post">
                <div class="am-form-group">
                    <input name="username" type="text" class="tpl-form-input" id="username" placeholder="请输入账号" required autofocus>
                </div>
                <div class="am-form-group">
                    <input name="password" type="password" class="tpl-form-input" id="password" placeholder="请输入密码" required>
                </div>
                <div class="am-form-group tpl-login-remember-me">
                    <input id="remember-me" type="checkbox">
                    <label for="remember-me">
                        记住密码
                    </label>
                </div>
                <div class="am-form-group">
                    <button type="submit" class="am-btn am-btn-primary  am-btn-block tpl-btn-bg-color-success  tpl-login-btn">提交</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div style="text-align:center;">
    <p style="font-size: small">©2016-2017 All Rights Reserved. Ryan. Privacy and Terms</p>
</div>
<script src="<%=basePath%>/js/theme.js"></script>
<script src="<%=basePath%>/js/amazeui.min.js"></script>
<script src="<%=basePath%>/js/app.js"></script>

</body>
</html>
