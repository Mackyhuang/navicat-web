<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="description"
          content="Vali is a responsive and free admin theme built with Bootstrap 4, SASS and PUG.js. It's fully customizable and modular.">
    <!-- Twitter meta-->
    <meta property="twitter:card" content="summary_large_image">
    <meta property="twitter:site" content="@pratikborsadiya">
    <meta property="twitter:creator" content="@pratikborsadiya">
    <!-- Open Graph Meta-->
    <meta property="og:type" content="website">
    <meta property="og:site_name" content="Vali Admin">
    <meta property="og:title" content="Vali - Free Bootstrap 4 admin theme">
    <meta property="og:url" content="http://pratikborsadiya.in/blog/vali-admin">
    <meta property="og:image" content="http://pratikborsadiya.in/blog/vali-admin/hero-social.png">
    <meta property="og:description"
          content="Vali is a responsive and free admin theme built with Bootstrap 4, SASS and PUG.js. It's fully customizable and modular.">
    <title>共享文件</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Main CSS-->
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <!-- Font-icon css-->
    <link rel="stylesheet" type="text/css"
          href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body class="app sidebar-mini rtl">
<!-- Navbar-->
<header class="app-header"><a class="app-header__logo" href="<%=request.getContextPath()%>/getdatabaseNames"></a>
    <!-- Sidebar toggle button--><a class="app-sidebar__toggle" href="#" data-toggle="sidebar"
                                    aria-label="Hide Sidebar"></a>
    <!-- Navbar Right Menu-->
    <ul class="app-nav">
        <!-- User Menu-->
        <li class="dropdown"><a class="app-nav__item" href="#" data-toggle="dropdown" aria-label="Open Profile Menu"><i
                class="fa fa-user fa-lg"></i></a>
            <ul class="dropdown-menu settings-menu dropdown-menu-right">
                <li><a class="dropdown-item" href="<%=request.getContextPath()%>/logout.do"><i
                        class="fa fa-sign-out fa-lg"></i> Logout</a></li>
            </ul>
        </li>
    </ul>
</header>
<!-- Sidebar menu-->
<div class="app-sidebar__overlay" data-toggle="sidebar"></div>
<aside class="app-sidebar">
    <div class="app-sidebar__user">
        <div>
            <img style="height: 50px" class="app-sidebar__user-avatar" src="./img/pdx.gif" alt="User Image">
        </div>
        <div>
            <p class="app-sidebar__user-name">欢迎您：</p>
            <p class="app-sidebar__user-designation">${user.uName}</p>
        </div>
    </div>
    <ul class="app-menu">
        <c:if test="${!empty tableList}">
            <li><a class="app-menu__item" href="<%=request.getContextPath()%>/getdatabaseNames"><i
                    class="app-menu__icon fa fa-dashboard"></i><span class="app-menu__label">返回数据库列表</span></a></li>
        </c:if>
        <c:if test="${!empty databaseList}">
            <li class="treeview is-expanded"><a class="app-menu__item  active" href="#" data-toggle="treeview"><i
                    class="app-menu__icon fa fa-th-list"></i><span class="app-menu__label">数据库</span><i
                    class="treeview-indicator fa fa-angle-right"></i></a>
                <ul class="treeview-menu">
                    <c:forEach var="database" items="${databaseList}">
                        <li><a class="treeview-item"
                               href="<%=request.getContextPath()%>/getTables?databaseName=${database}"><i
                                class="icon fa fa-circle-o"></i>${database}</a></li>
                    </c:forEach>
                </ul>
            </li>
        </c:if>
        <c:if test="${!empty tableList}">
            <li class="treeview is-expanded"><a class="app-menu__item  active" href="#" data-toggle="treeview"><i
                    class="app-menu__icon fa fa-th-list"></i><span class="app-menu__label">库表</span><i
                    class="treeview-indicator fa fa-angle-right"></i></a>
                <ul class="treeview-menu">
                    <c:forEach var="table" items="${tableList}">
                        <li><a class="treeview-item" href="<%=request.getContextPath()%>/getDetailTable?tableName=${table}"><i
                                class="icon fa fa-circle-o"></i>${table}</a></li>
                    </c:forEach>
                </ul>
            </li>
        </c:if>
    </ul>
</aside>
<main class="app-content">
    <div class="app-title">
        <div>
            <h1><i class="fa fa-th-list"></i>库表</h1>
            <p>这里展示您所选库表的数据</p>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="tile">
                <div class="tile-body">
                    <table class="table table-hover table-bordered" id="sampleTable">
                        <thead>
                        <tr>
                            <c:if test="${!empty columnProperty}">
                                <c:forEach var="column" items="${columnProperty}">
                                    <th>${column}</th>
                                </c:forEach>
                            </c:if>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty columnProperty}">
                            <tr>
                                <td colspan="100">请选择数据库和库表</td>
                            </tr>
                        </c:if>
                        <c:if test="${!empty detailTableInfo}">
                            <c:forEach var="info" items="${detailTableInfo}" varStatus="loop">
                                <tr>
                                    <c:forEach var="line" items="${info}">
                                        <td>${line}</td>
                                    </c:forEach>
                                    <td>
                                        <a href="#" class="delete_btn" data-index-tag="${primaryKeys[loop.count-1]}">删除</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</main>
<!-- Essential javascripts for application to work-->
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/main.js"></script>
<!-- The javascript plugin to display page loading on top-->
<script src="js/plugins/pace.min.js"></script>
<!-- Page specific javascripts-->
<!-- Data table plugin-->
<script type="text/javascript" src="js/plugins/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/plugins/dataTables.bootstrap.min.js"></script>
<script type="text/javascript">$('#sampleTable').DataTable();</script>
<!-- Google analytics script-->
<script type="text/javascript">
    if (document.location.hostname == 'pratikborsadiya.in') {
        (function (i, s, o, g, r, a, m) {
            i['GoogleAnalyticsObject'] = r;
            i[r] = i[r] || function () {
                (i[r].q = i[r].q || []).push(arguments)
            }, i[r].l = 1 * new Date();
            a = s.createElement(o),
                m = s.getElementsByTagName(o)[0];
            a.async = 1;
            a.src = g;
            m.parentNode.insertBefore(a, m)
        })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');
        ga('create', 'UA-72504830-1', 'auto');
        ga('send', 'pageview');
    }
</script>
<script>
    $(".delete_btn").each(function (index, item) {
        item = $(item);
        item.click(function () {
            $.ajax({
                url: "remove",
                dataType: "json",
                data:{
                    "index": item.attr("data-index-tag")
                },
                success:function (data) {
                    if (data == 1){
                        alert("删除成功！");
                        window.location.reload();
                    } else {
                        alert("删除失败！");
                    }
                },
                error:function (data) {
                    alert(data);
                }
            })
        })
    })
</script>
</body>
</html>