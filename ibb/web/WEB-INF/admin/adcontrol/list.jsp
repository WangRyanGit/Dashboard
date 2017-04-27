<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/21
  Time: 14:17
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
    <title>控制界面</title>
    <meta name="description" content="这是一个 adcontrol 页面">
    <meta name="keywords" content="adcontrol">
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
        </ul>
    </div>

    <!-- 内容区域 -->
    <div class="tpl-content-wrapper">
        <div class="row-content am-cf">
            <div class="row">
                <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                    <div class="widget am-cf">
                        <div class="widget-head am-cf">
                            <div class="widget-title  am-cf">控制列表 AdControl</div>

                        </div>
                        <div class="widget-body  am-fr">

                            <div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
                                <div class="am-form-group">
                                    <div class="am-btn-toolbar">
                                        <div class="am-btn-group am-btn-group-xs">
                                            <button type="button" class="am-btn am-btn-default am-btn-success" onClick="location.href='<%=basePath%>/control/toAddAdControl'"><span class="am-icon-plus"></span>新增</button>
                                            <button type="button" class="am-btn am-btn-default am-btn-danger" onClick="batchDelete()"><span class="am-icon-trash-o"></span> 删除</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <form id="form" method="get" action="<%=basePath%>/control/showAllAdControl">
                                <div class="am-u-sm-12 am-u-md-6 am-u-lg-3">
                                    <div class="am-form-group tpl-table-list-select">
                                        <select data-am-selected="{btnSize: 'sm'}" name="pkg">
                                            <option value="com">所有APP</option>
                                            <option value="com.listenmusicsoho">com.listenmusicsoho</option>
                                            <option value="com.music.listen">com.music.listen</option>
                                            <option value="com.onlinemusic.player">com.onlinemusic.player</option>
                                            <option value="com.FreeMusic20170213">com.FreeMusic20170213</option>
                                            <option value="com.YouTubePro">com.YouTubePro</option>
                                            <option value="com.shadowfollow.com">com.shadowfollow.com</option>
                                            <option value="com.shadowfollow.com">com.vpnActive.app</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="am-u-sm-12 am-u-md-12 am-u-lg-3">
                                    <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">
                                        <%--<input type="text" class="am-form-field ">--%>
                                        <span class="am-input-group-btn">
                                            <button class="am-btn  am-btn-default am-btn-success tpl-table-list-field am-icon-search" type="submit"></button>
                                        </span>
                                    </div>
                                </div>
                            </form>
                            <div class="am-u-sm-12">
                                <form id="form1" method="post" action="<%=basePath%>/control/showAllAdControl">
                                    <c:if test="${empty requestScope.pagelist}">
                                        <<---没有任何控制信息--->>
                                    </c:if>
                                    <c:if test="${!empty requestScope.pagelist}">
                                        <table width="100%" class="am-table am-table-compact am-table-striped tpl-table-black " id="example-r">
                                            <thead>
                                            <tr>
                                                <th><input type="checkbox" id="SelectAll" onClick="selectAll();"></th>
                                                <th>包名</th>
                                                <th>推广地区</th>
                                                <th>广告位</th>
                                                <th>状态</th>
                                                <th>操作</th>
                                            </tr>
                                            </thead>
                                            <c:forEach items="${requestScope.pagelist.lists}" var="pg">
                                                <tbody>
                                                <tr class="gradeX">
                                                    <td><input type="checkbox" id="check"
                                                               name="check" value="${pg.id }"></td>
                                                    <td>${pg.pkg}</td>
                                                    <td>${pg.country}</td>
                                                    <td>${pg.position}</td>
                                                    <td>
                                                        <c:if test="${pg.status == 1}"><font color="green"> 正常</font></c:if>
                                                        <c:if test="${pg.status == 0}"><font color="#a9a9a9"> 下线 </font></c:if>
                                                    </td>
                                                    <td>
                                                        <div class="tpl-table-black-operation">
                                                            <a href="<%=basePath%>/control/toEditAdControl?id=${pg.id}">
                                                                <i class="am-icon-pencil"></i> 编辑
                                                            </a>
                                                            <a href="<%=basePath%>/control/deleteAdControl?id=${pg.id}" class="tpl-table-black-operation-del" onclick="alert('确认删除')">
                                                                <i class="am-icon-trash"></i> 删除
                                                            </a>
                                                        </div>
                                                    </td>
                                                </tr>
                                                </tbody>
                                            </c:forEach>
                                        </table>
                                    </c:if>
                                </form>
                            </div>
                            <!-- 分页功能 start -->
                            <div class="am-u-lg-12 am-cf">

                                <div class="am-fr">
                                    <ul class="am-pagination tpl-pagination">
                                        <li class="am-disabled">第${requestScope.pagelist.currPage }/ ${requestScope.pagelist.totalPage}页</li>
                                        <c:if test="${requestScope.pagelist.currPage != 1}">
                                            <li><a href="${pageContext.request.contextPath }/control/showAllAdControl?currentPage=1&pkg=${requestScope.pagelist.pkg}">«</a></li>
                                            <li><a href="${pageContext.request.contextPath }/control/showAllAdControl?currentPage=${requestScope.pagelist.currPage-1}&pkg=${requestScope.pagelist.pkg}">‹</a></li>
                                        </c:if>
                                        <c:if test="${requestScope.pagelist.currPage != requestScope.pagelist.totalPage}">
                                            <li><a href="${pageContext.request.contextPath }/control/showAllAdControl?currentPage=${requestScope.pagelist.currPage+1}&pkg=${requestScope.pagelist.pkg}">›</a></li>
                                            <li><a href="${pageContext.request.contextPath }/control/showAllAdControl?currentPage=${requestScope.pagelist.totalPage}&pkg=${requestScope.pagelist.pkg}">»</a></li>
                                        </c:if>

                                    </ul>
                                    <ul class="am-pagination tpl-pagination">
                                        <li>Total:${requestScope.pagelist.totalCount }</li>
                                        <li>pageSize:${requestScope.pagelist.pageSize}</li>

                                    </ul>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<script type="text/javascript">
    /* 全选  */
    function selectAll(){
        if ($("#SelectAll").is(":checked")) {
            $(":checkbox").prop("checked", true);//所有选择框都选中
        } else {
            $(":checkbox").prop("checked", false);
        }
    }
    /*   批量删除   */
    function batchDelete(){
        if(confirm("真的要删除这批策略吗?")){
            $("#form1").attr("action","<%=basePath%>/control/batchDelete");
            $("#form1").submit();
        }
    }
</script>
<script src="<%=basePath%>/js/amazeui.min.js"></script>
<script src="<%=basePath%>/js/amazeui.datatables.min.js"></script>
<script src="<%=basePath%>/js/dataTables.responsive.min.js"></script>
<script src="<%=basePath%>/js/app.js"></script>

</body>

</html>


