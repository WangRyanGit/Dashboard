<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/8
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/20
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <title>策略界面</title>
    <meta name="description" content="这是一个 edit 页面">
    <meta name="keywords" content="edit">
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

<body data-type="widgets">
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
                <a href="javascript:;">
                    <i class="am-icon-calendar sidebar-nav-link-logo"></i> 日历[todo]
                </a>
            </li>
            <li class="sidebar-nav-link">
                <a href="javascript:;">
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
            <li class="sidebar-nav-link">
                <a href="javascript:;" class="sidebar-nav-sub-title">
                    <i class="am-icon-wpforms sidebar-nav-link-logo"></i> 用户列表
                    <span class="am-icon-chevron-down am-fr am-margin-right-sm sidebar-nav-sub-ico"></span>
                </a>
                <ul class="sidebar-nav sidebar-nav-sub">
                    <li class="sidebar-nav-link">
                        <a href="<%=basePath%>/vpncontrol/showAllVpnUserData">
                            <span class="am-icon-angle-right sidebar-nav-link-logo"></span> VPN用户
                        </a>
                    </li>
                    <li class="sidebar-nav-link">
                        <a href="<%=basePath%>/followcontrol/showAllFollowData">
                            <span class="am-icon-angle-right sidebar-nav-link-logo"></span> FOLLOW用户
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>

    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">

        <div class="row-content am-cf">

            <div class="row">

                <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title am-fl">编辑用户 Follow</div>
                            <div class="widget-function am-fr">
                                <a href="javascript:;" class="am-icon-cog"></a>
                            </div>
                        </div>
                        <div class="widget-body am-fr">


                            <form class="am-form tpl-form-border-form tpl-form-border-br" action="<%=basePath%>/followcontrol/editFollowPurchase" method="post">

                                <div class="am-form-group">
                                    <input type="hidden" class="tpl-form-input" name="id" value="${follow.id}" readonly><!-- 传id值 -->
                                </div>

                                <div class="am-form-group">
                                    <label class="am-u-sm-3 am-form-label">包名 <span class="tpl-form-line-small-title">BundleId</span></label>
                                    <div class="am-u-sm-9">
                                        <input type="text" class="tpl-form-input" name="pkg" value="${follow.pkg}" readonly>
                                        <small>不可修改。</small>
                                    </div>
                                </div>

                                <div class="am-form-group">
                                    <label class="am-u-sm-3 am-form-label">用户编号 <span class="tpl-form-line-small-title">User_id</span></label>
                                    <div class="am-u-sm-9">
                                        <input type="text" class="tpl-form-input" name="user_id" value="${follow.user_id}" readonly><!-- 传id值 -->
                                        <small>不可修改。</small>
                                    </div>
                                </div>

                                <div class="am-form-group">
                                    <label class="am-u-sm-3 am-form-label">预留 <span class="tpl-form-line-small-title">Reserve</span></label>
                                    <div class="am-u-sm-3">
                                        <input type="text" class="tpl-form-input" name="quantity" value="${follow.quantity}" readonly><span>quantity</span>
                                    </div>
                                    <div class="am-u-sm-3">
                                        <input type="text" class="tpl-form-input" name="product_id" value="${follow.product_id}" readonly><span>product_id</span>
                                    </div>
                                    <div class="am-u-sm-3">
                                        <input type="text" class="tpl-form-input" name="md5_receipt" value="${follow.md5_receipt}" readonly><span>md5_receipt</span>
                                    </div>
                                </div>

                                <div class="am-form-group">
                                    <label class="am-u-sm-3 am-form-label">开始时间 <span class="tpl-form-line-small-title">Purchase_date </span></label>
                                    <div class="am-u-sm-9">
                                        <input type="text" class="tpl-form-input" name="purchase_date" value="${follow.purchase_date}" readonly>
                                        <small>用户付费开始时间,一般不修改(format:yyyy-MM-dd HH:mm:ss)。</small>
                                    </div>
                                </div>

                                <div class="am-form-group">
                                    <label class="am-u-sm-3 am-form-label">到期时间 <span class="tpl-form-line-small-title">Expires_date</span></label>
                                    <div class="am-u-sm-9">
                                        <input type="text" class="tpl-form-input" name="expires_date" value="${follow.expires_date}">
                                        <small>用户付费到期时间,一般不修改(format:yyyy-MM-dd HH:mm:ss)。</small>
                                    </div>
                                </div>

                                <div class="am-form-group">
                                    <label class="am-u-sm-3 am-form-label">状态 <span class="tpl-form-line-small-title">Status</span></label>
                                    <div class="am-u-sm-9">
                                        <select data-am-selected="{searchBox: 1}" style="display: none;" name="status">
                                            <option value="${follow.status}" selected="selected">
                                                <c:if test="${follow.status == 0}"> 正常 </c:if>
                                                <c:if test="${follow.status == -1}"> 禁用 </c:if>
                                            </option>
                                            <option value="0">正常</option>
                                            <option value="-1">禁用</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="am-form-group">
                                    <div class="am-u-sm-9 am-u-sm-push-3">
                                        <button type="submit" class="am-btn am-btn-primary tpl-btn-bg-color-success ">提交</button>
                                    </div>
                                </div>
                            </form>
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
