<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/19
  Time: 15:27
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
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <title>主界面</title>
    <meta name="description" content="这是一个 main 页面">
    <meta name="keywords" content="main">
    <meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
    <meta name="renderer" content="webkit">
    <meta name="apple-mobile-web-app-title" content="Ryan" />
    <link rel="icon" type="image/png" href="<%=basePath%>/img/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="<%=basePath%>/img/app-icon72x72@2x.png">
    <script src="<%=basePath%>/js/echarts.min.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/css/amazeui.min.css" />
    <link rel="stylesheet" href="<%=basePath%>/css/amazeui.datatables.min.css" />
    <link rel="stylesheet" href="<%=basePath%>/css/app.css">
    <script src="<%=basePath%>/js/jquery.min.js"></script>
</head>

<body data-type="index">
<script src="<%=basePath%>/js/theme.js"></script>
<div class="am-g tpl-g">
    <!-- 头部 -->
    <header>
        <!-- logo -->
        <div class="am-fl tpl-header-logo">
            <a href="<%=basePath%>/dashboard/tomain">IOS工具事业部数据中心</a>
        </div>
        <!-- 右侧内容 -->
        <div class="tpl-header-fluid">
            <!-- 侧边切换 -->
            <div class="am-fl tpl-header-switch-button am-icon-list">
                    <span>

                </span>
            </div>
            <!-- 搜索 -->
            <div class="am-fl tpl-header-search">
                <form class="tpl-header-search-form" action="javascript:;">
                    <button class="tpl-header-search-btn am-icon-search"></button>
                    <input class="tpl-header-search-box" type="text" placeholder="搜索内容...">
                </form>
            </div>
            <!-- 其它功能-->
            <div class="am-fr tpl-header-navbar">
                <ul>
                    <!-- 欢迎语 -->
                    <li class="am-text-sm tpl-header-navbar-welcome">
                        <a href="javascript:;">欢迎你, <span>${username}</span> </a>
                    </li>
                    </li>

                    <!-- 退出 -->
                    <li class="am-text-sm">
                        <a href="<%=basePath%>/dashboard/tologin">
                            <span class="am-icon-sign-out"></span> 退出
                        </a>
                    </li>
                </ul>
            </div>
        </div>

    </header>
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
    <!-- 侧边导航栏 -->
    <div class="left-sidebar">
        <!-- 用户信息 -->
        <div class="tpl-sidebar-user-panel">
            <div class="tpl-user-panel-slide-toggleable">
                <div class="tpl-user-panel-profile-picture">
                    <img src="<%=basePath%>/img/user.png" alt="">
                </div>
                <span class="user-panel-logged-in-text">
              <i class="am-icon-circle-o am-text-success tpl-user-panel-status-icon"></i>
              IOS
          </span>
                <a href="javascript:;" class="tpl-user-panel-action-link"> <span class="am-icon-pencil"></span> 账号设置</a>
            </div>
        </div>

        <!-- 菜单 -->
        <ul class="sidebar-nav">
            <li class="sidebar-nav-link">
                <a href="<%=basePath%>/dashboard/tomain" class="active">
                    <i class="am-icon-home sidebar-nav-link-logo"></i> 首页
                </a>
            </li>
            <li class="sidebar-nav-link">
                <a href="<%=basePath%>admin/calendar.jsp">
                    <i class="am-icon-calendar sidebar-nav-link-logo"></i> 日历[todo]
                </a>
            </li>
            <li class="sidebar-nav-link">
                <a href="<%=basePath%>admin/chart.jsp">
                    <i class="am-icon-bar-chart sidebar-nav-link-logo"></i> 图表预留[todo]

                </a>
            </li>
            <li class="sidebar-nav-link">
                <a href="javascript:;" class="sidebar-nav-sub-title">
                    <i class="am-icon-table sidebar-nav-link-logo"></i> 数据列表
                    <span class="am-icon-chevron-down am-fr am-margin-right-sm sidebar-nav-sub-ico"></span>
                </a>
                <ul class="sidebar-nav sidebar-nav-sub">
                    <li class="sidebar-nav-link">
                        <a href="<%=basePath%>/strategy/showAllAdStrategy">
                            <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 策略列表
                        </a>
                    </li>

                    <li class="sidebar-nav-link">
                        <a href="<%=basePath%>/control/showAllAdControl">
                            <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 控制列表
                        </a>
                    </li>
                    <li class="sidebar-nav-link">
                        <a href="<%=basePath%>/click/showAllAdClick">
                            <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 点击频率
                        </a>
                    </li>
                    <li class="sidebar-nav-link">
                        <a href="<%=basePath%>/push/showAllAdPush">
                            <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 推送列表
                        </a>
                    </li>
                    <li class="sidebar-nav-link">
                        <a href="<%=basePath%>/resources/showAllAdResources">
                            <span class="am-icon-angle-right sidebar-nav-link-logo"></span> 资源列表
                        </a>
                    </li>
                </ul>
            </li>
            <%--<li class="sidebar-nav-link">
                <a href="sign-up.html">
                    <i class="am-icon-clone sidebar-nav-link-logo"></i> 注册
                    <span class="am-badge am-badge-secondary sidebar-nav-link-logo-ico am-round am-fr am-margin-right-sm">6</span>
                </a>
            </li>--%>
        </ul>
    </div>

    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">

        <div class="container-fluid am-cf">
            <div class="row">
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-9">
                    <div class="page-header-heading"><span class="am-icon-home page-header-heading-icon"></span> 首页 </div>
                </div>
            </div>

        </div>

        <div class="row-content am-cf">
            <div class="row  am-cf">
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-4">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">月度财务收支计划</div>
                            <div class="widget-function am-fr">
                                <a href="javascript:;" class="am-icon-cog"></a>
                            </div>
                        </div>
                        <div class="widget-body am-fr">
                            <div class="am-fl">
                                <div class="widget-fluctuation-period-text">
                                    ￥6746.45
                                    <button class="widget-fluctuation-tpl-btn">
                                        <i class="am-icon-calendar"></i>
                                        更多月份
                                    </button>
                                </div>
                            </div>
                            <div class="am-fr am-cf">
                                <div class="widget-fluctuation-description-amount text-success" am-cf>
                                    +￥3420.56

                                </div>
                                <div class="widget-fluctuation-description-text am-text-right">
                                    4月份收入
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="am-u-sm-12 am-u-md-6 am-u-lg-4">
                    <div class="widget widget-primary am-cf">
                        <div class="widget-statistic-header">
                            本季度KPI
                        </div>
                        <div class="widget-statistic-body">
                            <div class="widget-statistic-value">
                                ￥10000
                            </div>
                            <div class="widget-statistic-description">
                                本季度比去年要多收入 <strong>3000元</strong> 人民币
                            </div>
                            <span class="widget-statistic-icon am-icon-credit-card-alt"></span>
                        </div>
                    </div>
                </div>
                <div class="am-u-sm-12 am-u-md-6 am-u-lg-4">
                    <div class="widget widget-purple am-cf">
                        <div class="widget-statistic-header">
                            本季度利润
                        </div>
                        <div class="widget-statistic-body">
                            <div class="widget-statistic-value">
                                ￥2294
                            </div>
                            <div class="widget-statistic-description">
                                本季度比去年多收入 <strong>2593元</strong> 人民币
                            </div>
                            <span class="widget-statistic-icon am-icon-support"></span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row am-cf">
                <div class="am-u-sm-12 am-u-md-8">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">本周用户自然增长量</div>
                            <div class="widget-function am-fr">
                                <a href="javascript:;" class="am-icon-cog"></a>
                            </div>
                        </div>
                        <div class="widget-body-md widget-body tpl-amendment-echarts am-fr" id="tpl-echarts">

                        </div>
                    </div>
                </div>

                <div class="am-u-sm-12 am-u-md-4">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">月度完成实况</div>
                            <div class="widget-function am-fr">
                                <a href="javascript:;" class="am-icon-cog"></a>
                            </div>
                        </div>
                        <div class="widget-body widget-body-md am-fr">

                            <div class="am-progress-title">Data Load <span class="am-fr am-progress-title-more">24 / 30</span></div>
                            <div class="am-progress">
                                <div class="am-progress-bar" style="width: 80%"></div>
                            </div>
                            <div class="am-progress-title">Money Load <span class="am-fr am-progress-title-more">6000 / 10000</span></div>
                            <div class="am-progress">
                                <div class="am-progress-bar  am-progress-bar-warning" style="width: 60%"></div>
                            </div>
                            <div class="am-progress-title">KPI Load <span class="am-fr am-progress-title-more">6000 / 10000</span></div>
                            <div class="am-progress">
                                <div class="am-progress-bar am-progress-bar-danger" style="width: 60%"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<script src="<%=basePath%>/js/amazeui.min.js"></script>
<script src="<%=basePath%>/js/amazeui.datatables.min.js"></script>
<script src="<%=basePath%>/js/dataTables.responsive.min.js"></script>
<script src="<%=basePath%>/js/app.js"></script>

</body>

</html>
