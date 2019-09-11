<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<title>面板</title>
<link rel="stylesheet"
	href="${ctx}/static/plugins/adminlte/css/AdminLTE.min.css">
<link rel="stylesheet"
	href="${ctx}/static/plugins/adminlte/css/all-skins.min.css">
<link rel="stylesheet"
	href="${ctx}/static/plugins/bootstrap/css/bootstrap.css">
<link rel="stylesheet"
	href="${ctx}/static/plugins/adminlte/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${ctx}/static/plugins/adminlte/bower_components/Ionicons/css/ionicons.min.css">
<link rel="stylesheet" href="${ctx}/static/css/dashboard.css">
</head>
<body>
	<!-- 顶部横栏开始 -->
	<div class="row">
		<div class="col-lg-3 col-xs-6">
			<div class="small-box bg-aqua">
				<div class="inner">
					<h3>150</h3>
					<p>今日订单数</p>
				</div>
				<div class="icon">
					<i class="ion ion-bag"></i>
				</div>
				<a href="javascript:void(0);" class="small-box-footer">查询订单&nbsp;<i
					class="fa fa-arrow-circle-right"></i></a>
			</div>
		</div>
		<div class="col-lg-3 col-xs-6">
			<div class="small-box bg-green">
				<div class="inner">
					<h3>51</h3>
					<p>今日活跃客户数</p>
				</div>
				<div class="icon">
					<i class="ion ion-stats-bars"></i>
				</div>
				<a href="javascript:void(0);" class="small-box-footer">App客户管理&nbsp;<i
					class="fa fa-arrow-circle-right"></i></a>
			</div>
		</div>
		<div class="col-lg-3 col-xs-6">
			<div class="small-box bg-yellow">
				<div class="inner">
					<h3>66</h3>
					<p>本月新注册用户数</p>
				</div>
				<div class="icon">
					<i class="ion ion-person-add"></i>
				</div>
				<a href="javascript:void(0);" class="small-box-footer">App客户管理&nbsp;<i
					class="fa fa-arrow-circle-right"></i></a>
			</div>
		</div>
		<div class="col-lg-3 col-xs-6">
			<div class="small-box bg-red">
				<div class="inner">
					<h3>65%</h3>
					<p>资金流入占比</p>
				</div>
				<div class="icon">
					<i class="ion ion-pie-graph"></i>
				</div>
				<a href="javascript:void(0);" class="small-box-footer">交易流水查询&nbsp;<i
					class="fa fa-arrow-circle-right"></i></a>
			</div>
		</div>
	</div>
	<!-- 顶部横栏结束 -->

	<!-- 主要内容开始 -->
	<div class="row">
		<!-- 左竖栏开始 -->
		<section class="col-lg-7">
			<div class="box box-primary">
				<div class="box-header with-border">
					<h3 class="box-title">Chart 1</h3>
					<div class="box-tools pull-right">
						<button type="button" class="btn btn-box-tool"
							data-widget="collapse">
							<i class="fa fa-minus"></i>
						</button>
						<button type="button" class="btn btn-box-tool" data-widget="remove">
							<i class="fa fa-times"></i>
						</button>
					</div>
				</div>
				<div class="box-body" >
					<div id="chart1" style="height:500px;"></div>
				</div>
			</div>
		</section>
		<!-- 左竖栏结束 -->

		<!-- 右竖栏开始 -->
		<section class="col-lg-5">
			<div class="box box-danger">
				<div class="box-header with-border">
					<h3 class="box-title">Chart 2</h3>
					<div class="box-tools pull-right">
						<button type="button" class="btn btn-box-tool"
							data-widget="collapse">
							<i class="fa fa-minus"></i>
						</button>
						<button type="button" class="btn btn-box-tool" data-widget="remove">
							<i class="fa fa-times"></i>
						</button>
					</div>
				</div>
				<div class="box-body" >
					<div id="chart2" style="height:400px;"></div>
				</div>
			</div>
		</section>
		<!-- 右竖栏结束 -->
	</div>
	<!-- 主要内容结束 -->
</body>
<script src="${ctx}/static/plugins/jquery/jquery-3.2.1.min.js"></script>
<script src="${ctx}/static/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}/static/plugins/adminlte/app.js"></script>
<script src="${ctx}/static/js/vue.min.js"></script>
<script src="${ctx}/static/js/router.js"></script>
<script src="${ctx}/static/plugins/layer/layer.js"></script>
<script src="${ctx}/static/plugins/echarts/js/echarts.js"></script>
<script src="${ctx}/static/js/dashboard.js"></script>

</html>