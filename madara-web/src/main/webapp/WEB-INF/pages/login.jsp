<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${ctx}/static/plugins/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/static/plugins/adminlte/css/AdminLTE.min.css">
    <link rel="stylesheet"
          href="${ctx}/static/plugins/bootstrapValidator/css/bootstrapValidator.min.css">
    <link rel="stylesheet" href="${ctx}/static/css/main.css">
    <link rel="shortcut icon" href="#"/>
    <title>Madara</title>
</head>
<!--[if lt IE 9]>
<script type="text/javascript">
    document.write("<div style='position: fixed; top: 0; left: 0; right: 0; bottom: 0; z-index: 100; width: 100%; height: 100%; padding-top: 200px;  background-color: #fff'><P  style='font-size: 50px; text-align: center'>请使用IE9及以上浏览器或者谷歌浏览器！</P></div>")
</script>
<![endif]-->

<body class="hold-transition login-page">
<div id="app" v-cloak>
    <div class="login-box">
        <div class="login-logo">
            <span class="label label-default">Madara web</span>
        </div>
        <!-- /.login-logo -->
        <div class="login-box-body">
            <p class="login-box-msg"><span class="badge">请登录</span></p>
            <div v-if="error" class="alert alert-danger alert-dismissible">
                <h4 style="margin-bottom: 0px;">
                    <i class="fa fa-exclamation-triangle"></i> {{errorMsg}}
                </h4>
            </div>
            <div class="form-group has-feedback">
                <input type="text" id="username" class="form-control" v-model="username" placeholder="用户名"> <span
                    class="glyphicon glyphicon-user form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" id="password" class="form-control" v-model="password" placeholder="密码"> <span
                    class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="text" class="form-control" v-model="captcha" @keyup.enter="login" placeholder="验证码"> <span
                    class="glyphicon glyphicon-warning-sign form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <img alt="如果看不清楚，请单击图片刷新！" class="pointer" :src="src" @click="refreshCode"> &nbsp;&nbsp;&nbsp;&nbsp;<a
                    href="javascript:;" @click="refreshCode">点击刷新</a>
            </div>


            <div class="row">
                <div class="col-xs-8">
                    <div class="checkbox icheck"></div>
                </div>
                <!-- /.col -->
                <div class="col-xs-4">
                    <button type="button" class="btn btn-success btn-block btn-flat" @click="login">登录</button>
                </div>
                <!-- /.col -->
            </div>
            <!-- /.social-auth-links -->
        </div>
    </div>
</div>
</body>


<script src="${ctx}/static/plugins/jquery/jquery-3.2.1.min.js"></script>
<script src="${ctx}/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}/static/plugins/adminlte/app.js"></script>
<script src="${ctx}/static/js/vue.min.js"></script>
<script src="${ctx}/static/plugins/layer/layer.js"></script>
<script src="${ctx}/static/js/login.js"></script>
<script src="${ctx}/static/plugins/bootstrapValidator/js/bootstrapValidator.min.js"></script>
</html>