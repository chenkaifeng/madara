<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Madara</title>
    <!-- AdminLTE配置 -->
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="${ctx}/static/plugins/bootstrap/css/bootstrap.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${ctx}/static/plugins/adminlte/css/font-awesome.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${ctx}/static/plugins/adminlte/css/AdminLTE.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="${ctx}/static/plugins/adminlte/css/all-skins.min.css">
    <link rel="stylesheet" href="${ctx}/static/css/main.css">
</head>

<!-- ADD THE CLASS layout-boxed TO GET A BOXED LAYOUT -->
<body class="hold-transition skin-green sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper" id="app" v-cloak>
    <header class="main-header">
        <a href="javascript:void(0);" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini"><b>Madara</b></span>
            <span class="logo-lg"><b>Madara</b></span>
        </a>
        <nav class="navbar navbar-static-top" role="navigation">
            <!-- Sidebar toggle button-->
            <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">Toggle navigation</span>
            </a>
            <div style="float:left;color:#fff;padding:15px 10px;">欢迎&nbsp;{{curUserInfo.loginName}}</div>
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <li class="dropdown user user-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                            <i class="fa fa-user-circle"></i> &nbsp;我的</span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-- User image -->
                            <li class="user-header">
                                <img src="${ctx}/static/plugins/adminlte/bower_components/Ionicons/png/512/android-contact.png"
                                     class="img-circle" alt="User Image">
                                <p>
                                    姓名：{{curUserInfo.userName}}
                                    <small>{{curUserInfo.corpNo}} {{curUserInfo.corpName}}</small>
                                </p>
                            </li>
                            <!-- Menu Body -->
                            <li class="user-body">
                                <div class="row">
                                    <div class="col-xs-12 text-center">
                                        登录名：{{curUserInfo.loginName}}
                                    </div>
                                    <div class="col-xs-12 text-center">
                                        角色：
                                    </div>
                                </div>

                                <!-- /.row -->
                            </li>
                            <!-- Menu Footer-->
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="#" @click="updatePassword" class="btn btn-success btn-flat">修改密码</a>
                                </div>
                                <div class="pull-right">
                                    <a href="#" @click="logout" class="btn btn-success btn-flat">退出系统</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <li><a href="javascript:;" @click="logout"><i class="fa fa-sign-out"></i> &nbsp;退出系统</a></li>
                </ul>
            </div>
        </nav>
    </header>

    <!-- Left side column. contains the sidebar -->
    <aside class="main-sidebar">
        <section class="sidebar">
            <!-- /.search form -->
            <!-- sidebar menu: : style can be found in sidebar.less -->
            <ul class="sidebar-menu">
                <li class="header">导航菜单</li>
                <li>
                    <a href="${ctx}/"><i class="fa fa-home"></i><span>首页</span></a>
                </li>
                <!-- vue生成的菜单 -->
                <menu-item :item="item" v-for="item in menuList"></menu-item>
            </ul>
        </section>
    </aside>

    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <ol class="breadcrumb" id="nav_title" style="position:static;float:none;">
                <li class="active"><i class="fa fa-home"
                                      style="font-size:20px;position:relative;top:2px;left:-3px;"></i> &nbsp;<a
                        href="${ctx}/">首页</a></li>
                <li class="active" v-show="navCatalogTitle!='dashboard'">{{navCatalogTitle}}</li>
                <li class="active" v-show="navCatalogTitle!='dashboard'">{{navTitle}}</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content" style="background:#fff;">
            <iframe scrolling="yes" frameborder="0"
                    style="width:100%;min-height:200px;overflow:visible;background:#fff;" :src="mainPage"></iframe>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <footer class="main-footer">
        Copyright &copy; 2019 All Rights Reserved
    </footer>

    <!-- Add the sidebar's background. This div must be placed
         immediately after the control sidebar -->
    <div class="control-sidebar-bg"></div>

    <!-- 修改密码 -->
    <div id="passwordLayer" style="display: none;">
        <form class="form-horizontal" style="width:550px;padding-top:20px;">
            <div class="form-group">
                <div class="form-group">
                    <div class="col-sm-2 control-label">原密码</div>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" v-model="oldPassword" placeholder="原密码"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2 control-label">新密码</div>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" v-model="newPassword" placeholder="新密码"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2 control-label">新密码确认</div>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" v-model="newPasswordConfirm" placeholder="新密码确认"/>
                    </div>
                </div>
            </div>
        </form>
    </div>

</div>

<script src="${ctx}/static/plugins/jquery/jquery-3.2.1.min.js"></script>
<script src="${ctx}/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}/static/plugins/adminlte/app.js"></script>
<script src="${ctx}/static/js/vue.min.js"></script>
<script src="${ctx}/static/js/router.js"></script>
<script src="${ctx}/static/plugins/layer/layer.js"></script>
<script src="${ctx}/static/js/index.js"></script>
</body>
</html>